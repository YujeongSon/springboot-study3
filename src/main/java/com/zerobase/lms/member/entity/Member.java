package com.zerobase.lms.member.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Member implements MemberCode {
    @Id
    private String userId;

    private String userName;
    private String password;
    private String phone;
    private LocalDateTime regDate;

    private boolean emailAuthYn;
    private LocalDateTime emailAuthDate;
    private String emailAuthKey;

    private String resetPasswordKey;
    private LocalDateTime resetPasswordLimitDate;

    private boolean adminYn;

    private String userStatus;
}
