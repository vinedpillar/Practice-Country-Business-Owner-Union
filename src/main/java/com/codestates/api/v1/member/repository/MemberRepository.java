package com.codestates.api.v1.member.repository;

import com.codestates.api.v1.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(long memberId);
}
