package com.zerobase.lms.member.service;

import com.zerobase.lms.admin.dto.MemberDto;
import com.zerobase.lms.admin.model.MemberParam;
import com.zerobase.lms.member.model.MemberInput;
import com.zerobase.lms.member.model.ResetPasswordInput;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface MemberService extends UserDetailsService {

    boolean register(MemberInput parameter);

    // uuid에 해당하는 계정 활성화
    boolean emailAuth(String uuid);

    // 입력한 이메일로 비밀번호 초기화 정보 전송
    boolean sendResetPassword(ResetPasswordInput parameter);

    // 입력받은 uuid로 비밀번호 초기화
    boolean resetPassword(String uuid, String password);

    // 입력받은 uuid가 유효한지 확인
    boolean checkResetPassword(String uuid);

    // 회원 목록(관리자용)
    List<MemberDto> list(MemberParam parameter);

    // 회원 상세 정보(관리자용)
    MemberDto detail(String userId);

    // 회원 상태 변경(관리자용)
    boolean updateStatus(String userId, String userStatus);

    // 회원 비밀번호 초기화(관리자용)
    boolean updatePassword(String userId, String password);
}
