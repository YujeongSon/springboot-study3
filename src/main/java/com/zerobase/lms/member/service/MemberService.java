package com.zerobase.lms.member.service;

import com.zerobase.lms.member.model.MemberDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {

    boolean register(MemberDto memberDto);

    // uuid에 해당하는 계정 활성화
    boolean emailAuth(String uuid);
}
