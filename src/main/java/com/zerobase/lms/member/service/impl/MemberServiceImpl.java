package com.zerobase.lms.member.service.impl;

import com.zerobase.lms.admin.dto.MemberDto;
import com.zerobase.lms.admin.mapper.MemberMapper;
import com.zerobase.lms.admin.model.MemberParam;
import com.zerobase.lms.components.MailComponent;
import com.zerobase.lms.member.entity.Member;
import com.zerobase.lms.member.entity.MemberCode;
import com.zerobase.lms.member.exception.MemberNotEmailAuthException;
import com.zerobase.lms.member.exception.MemberStopUserException;
import com.zerobase.lms.member.model.MemberInput;
import com.zerobase.lms.member.model.ResetPasswordInput;
import com.zerobase.lms.member.repository.MemberRepository;
import com.zerobase.lms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MailComponent mailComponent;
    private final MemberMapper memberMapper;

    // 회원 가입
    @Override
    public boolean register(MemberInput memberInput) {

        Optional<Member> optionalMember = memberRepository.findById(memberInput.getUserId());
        if (optionalMember.isPresent()) {
            // 이미 해당 userId 존재
            return false;
        }

        String encPassword = BCrypt.hashpw(memberInput.getPassword(), BCrypt.gensalt()); // 비밀번호 암호화

        String uuid = UUID.randomUUID().toString();

        Member member = Member.builder()
                .userId(memberInput.getUserId())
                .userName(memberInput.getUserName())
                .password(encPassword)
                .phone(memberInput.getPhone())
                .regDate(LocalDateTime.now())
                .emailAuthYn(false)
                .emailAuthKey(uuid)
                .userStatus(Member.MEMBER_STATUS_REQ)
                .build();

        memberRepository.save(member);

        String email = memberInput.getUserId();
        String subject = "lms 사이트 가입을 축하드립니다.";
        String text = "<p>lms 사이트 가입을 축하드립니다.</p>"+
                    "<p>아래 링크를 클릭하여 가입을 완료해주세요.</p>"+
                    "<div><a target='_blank' href='http://localhost:8080/member/email/auth?id="+
                    uuid +"'>가입 완료</a></div>";
        mailComponent.sendMail(email, subject, text);

        return true;
    }

    @Override
    public boolean emailAuth(String uuid) {

        Optional<Member> optionalMember = memberRepository.findByEmailAuthKey(uuid);
        if (!optionalMember.isPresent()) {
            return false;
        }

        Member member = optionalMember.get();

        if (member.isEmailAuthYn()) { // 이미 이메일 활성화가 되어있는 경우
            return false;
        }

        member.setUserStatus(MemberCode.MEMBER_STATUS_ING);
        member.setEmailAuthYn(true);
        member.setEmailAuthDate(LocalDateTime.now());
        memberRepository.save(member);

        return true;
    }

    @Override
    public boolean sendResetPassword(ResetPasswordInput passwordInput) {

        Optional<Member> optionalMember = memberRepository.findByUserIdAndUserName(
                passwordInput.getUserId(), passwordInput.getUserName());

        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        Member member = optionalMember.get();

        String uuid = UUID.randomUUID().toString();
        member.setResetPasswordKey(uuid);
        member.setResetPasswordLimitDate(LocalDateTime.now().plusDays(1));
        memberRepository.save(member);

        String email = passwordInput.getUserId();
        String subject = "[lms] 비밀번호 초기화 메일입니다.";
        String text = "<p>lms 비밀번호 초기화 메일입니다.</p>"+
                "<p>아래 링크를 클릭하여 비밀번호를 초기화 해주세요.</p>"+
                "<div><a target='_blank' href='http://localhost:8080/member/reset/password?id="+
                uuid +"'>비밀번호 초기화</a></div>";
        mailComponent.sendMail(email, subject, text);

        return true;
    }

    @Override
    public boolean resetPassword(String uuid, String password) {

        Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(uuid);

        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        Member member = optionalMember.get();

        // 비밀번호 초기화 유효기간 확인
        if (member.getResetPasswordLimitDate() == null) {
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }

        if (member.getResetPasswordLimitDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }

        String encPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        member.setPassword(encPassword);
        member.setResetPasswordKey(null);
        member.setResetPasswordLimitDate(null);
        memberRepository.save(member);

        return true;
    }

    @Override
    public boolean checkResetPassword(String uuid) {

        Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(uuid);

        if (!optionalMember.isPresent()) {
            return false;
        }

        Member member = optionalMember.get();

        // 비밀번호 초기화 유효기간 확인
        if (member.getResetPasswordLimitDate() == null) {
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }

        if (member.getResetPasswordLimitDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }

        return true;
    }

    @Override
    public List<MemberDto> list(MemberParam memberParam) {

        long totalCount = memberMapper.selectListCount(memberParam);
        List<MemberDto> list = memberMapper.selectList(memberParam);
        if (!CollectionUtils.isEmpty(list)) {
            int i = 0;
            for(MemberDto member : list) {
                member.setTotalCount(totalCount);
                member.setSeq(totalCount - memberParam.getStartPage() - i);
                i++;
            }
        }

        return list;
    }

    @Override
    public MemberDto detail(String userId) {

        Optional<Member> optionalMember = memberRepository.findById(userId);
        if (!optionalMember.isPresent()) {
            return null;
        }

        Member member = optionalMember.get();

        return MemberDto.of(member);
    }

    @Override
    public boolean updateStatus(String userId, String userStatus) {

        Optional<Member> optionalMember = memberRepository.findById(userId);
        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        Member member = optionalMember.get();
        member.setUserStatus(userStatus);
        memberRepository.save(member);

        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Member> optionalMember = memberRepository.findById(username);
        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        Member member = optionalMember.get();

        if (!member.isEmailAuthYn()) {
            throw new MemberNotEmailAuthException("이메일 활성화 이후에 로그인 해주세요.");
        }

        if (Member.MEMBER_STATUS_STOP.equals(member.getUserStatus())) {
            throw new MemberStopUserException("정지된 회원입니다.");
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        if (member.isAdminYn()) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return new User(member.getUserId(), member.getPassword(), grantedAuthorities);
    }
}
