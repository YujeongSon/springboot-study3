package com.zerobase.lms.member.service;

import com.zerobase.lms.admin.dto.MemberDto;
import com.zerobase.lms.admin.model.MemberParam;
import com.zerobase.lms.member.model.MemberInput;
import com.zerobase.lms.member.model.ResetPasswordInput;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface MemberService extends UserDetailsService {

    boolean register(MemberInput memberInput);

    // uuid에 해당하는 계정 활성화
    boolean emailAuth(String uuid);

    // 입력한 이메일로 비밀번호 초기화 정보 전송
    boolean sendResetPassword(ResetPasswordInput passwordInput);

    // 입력받은 uuid로 비밀번호 초기화
    boolean resetPassword(String uuid, String password);

    // 입력받은 uuid가 유효한지 확인
    boolean checkResetPassword(String uuid);

    // 회원 목록 -> 관리자만 사용 가능
    List<MemberDto> list(MemberParam memberParam);
}
