package com.zerobase.lms.member.repository;

import com.zerobase.lms.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
}
