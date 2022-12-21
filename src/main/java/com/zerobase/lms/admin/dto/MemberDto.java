package com.zerobase.lms.admin.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class MemberDto {

    String userId;
    String userName;
    String password;
    String phone;
    LocalDateTime regDate;

    boolean emailAuthYn;
    LocalDateTime emailAuthDate;
    String emailAuthKey;

    String resetPasswordKey;
    LocalDateTime resetPasswordLimitDate;

    boolean adminYn;
}
