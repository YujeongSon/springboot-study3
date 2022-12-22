package com.zerobase.lms.admin.dto;

import com.zerobase.lms.member.entity.Member;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    String userStatus;

    long totalCount;
    long seq;

    public static MemberDto of(Member member) {

        return MemberDto.builder()
                .userId(member.getUserId())
                .userName(member.getUserName())
                .phone(member.getPhone())
                .regDate(member.getRegDate())
                .emailAuthYn(member.isEmailAuthYn())
                .emailAuthDate(member.getEmailAuthDate())
                .emailAuthKey(member.getEmailAuthKey())
                .resetPasswordKey(member.getResetPasswordKey())
                .resetPasswordLimitDate(member.getResetPasswordLimitDate())
                .adminYn(member.isAdminYn())
                .userStatus(member.getUserStatus())
                .build();
    }
}
