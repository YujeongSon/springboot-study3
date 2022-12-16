package com.zerobase.lms.member.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private String userId;
    private String userName;
    private String password;
    private String phone;
}
