package com.zerobase.lms.member.service;

import com.zerobase.lms.member.model.MemberDto;

public interface MemberService {

    boolean register(MemberDto memberDto);

    // uuid에 해당하는 계정 활성화
    boolean emailAuth(String uuid);
}
