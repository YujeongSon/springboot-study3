package com.zerobase.lms.member.service.impl;

import com.zerobase.lms.components.MailComponent;
import com.zerobase.lms.member.entity.Member;
import com.zerobase.lms.member.exception.MemberNotEmailAuthException;
import com.zerobase.lms.member.model.MemberDto;
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

    // 회원 가입
    @Override
    public boolean register(MemberDto memberDto) {

        Optional<Member> optionalMember = memberRepository.findById(memberDto.getUserId());
        if (optionalMember.isPresent()) {
            // 이미 해당 userId 존재
            return false;
        }

        String encPassword = BCrypt.hashpw(memberDto.getPassword(), BCrypt.gensalt());

        String uuid = UUID.randomUUID().toString();

        Member member = Member.builder()
                .userId(memberDto.getUserId())
                .userName(memberDto.getUserName())
                .password(encPassword)
                .phone(memberDto.getPhone())
                .regDate(LocalDateTime.now())
                .emailAuthYn(false)
                .emailAuthKey(uuid)
                .build();
        memberRepository.save(member);

        String email = memberDto.getUserId();
        String subject = "lms 사이트 가입을 축하드립니다.";
        String text = "<p>lms 사이트 가입을 축하드립니다.</p>"+
                    "<p>아래 링크를 클릭하여 가입을 완료해주세요.</p>"+
                    "<div><a target='_blank' href='http://localhost:8080/member/email-auth?id="+
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
        member.setEmailAuthYn(true);
        member.setEmailAuthDate(LocalDateTime.now());
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

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new User(member.getUserId(), member.getPassword(), grantedAuthorities);
    }
}
