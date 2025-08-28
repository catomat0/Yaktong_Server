package com.example.YakTong.domain.member.repository;

import com.example.YakTong.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
