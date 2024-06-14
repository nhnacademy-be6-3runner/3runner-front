package com.nhnacademy.bookstore.member.member.repository;

import com.nhnacademy.bookstore.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
