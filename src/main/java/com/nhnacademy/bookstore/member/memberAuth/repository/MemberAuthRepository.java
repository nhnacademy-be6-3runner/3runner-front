package com.nhnacademy.bookstore.member.memberAuth.repository;

import com.nhnacademy.bookstore.entity.memberAuth.MemberAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberAuthRepository extends JpaRepository<MemberAuth, Long> {

}
