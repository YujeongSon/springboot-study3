package com.zerobase.lms.member.service;

import com.zerobase.lms.member.entity.Member;
import com.zerobase.lms.member.model.MemberDto;
import com.zerobase.lms.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    // 회원 가입
    @Override
    public boolean register(MemberDto memberDto) {

        Optional<Member> optionalMember = memberRepository.findById(memberDto.getUserId());
        if (optionalMember.isPresent()) {
            // 이미 해당 userId 존재
            return false;
        }

        Member member = new Member();
        member.setUserId(memberDto.getUserId());
        member.setUserName(memberDto.getUserName());
        member.setPassword(memberDto.getPassword());
        member.setPhone(memberDto.getPhone());
        member.setRegDate(LocalDateTime.now());

        memberRepository.save(member);

        return true;
    }
}
