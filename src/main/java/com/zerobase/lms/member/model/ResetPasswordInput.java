package com.zerobase.lms.member.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordInput {
    private String userId;
    private String userName;

    private String id;
    private String password;
}
