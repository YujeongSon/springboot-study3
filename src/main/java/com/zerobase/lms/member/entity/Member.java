package com.zerobase.lms.member.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {
    @Id
    private String userId;

    private String userName;
    private String password;
    private String phone;
    private LocalDateTime regDate;
}
