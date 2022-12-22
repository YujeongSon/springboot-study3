package com.zerobase.lms.member.entity;

public interface MemberCode {

    // 가입 요청 진행 중
    String MEMBER_STATUS_REQ = "REQ";

    // 이용 중인 상태
    String MEMBER_STATUS_ING = "ING";

    // 정지된 상태
    String MEMBER_STATUS_STOP = "STOP";
}
