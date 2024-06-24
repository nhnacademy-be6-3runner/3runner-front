package com.nhnacademy.bookstore.member.member.service;


import com.nhnacademy.bookstore.entity.member.Member;
import com.nhnacademy.bookstore.entity.member.enums.Grade;
import com.nhnacademy.bookstore.entity.member.enums.Status;
import com.nhnacademy.bookstore.member.member.dto.request.UpdateMemberRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public interface MemberService {
    Member save(Member member);

    Member readById(Long id);

    Member readByEmailAndPassword(String email, String password);

    Member updateMember(String memberId, UpdateMemberRequest updateMemberRequest);

    void deleteMember(String memberId);

    Member updateStatus(String memberId, Status status);

    Member updateGrade(String memberId, Grade grade);
    Member updateLastLogin(String memberId, ZonedDateTime lastLogin);
}
