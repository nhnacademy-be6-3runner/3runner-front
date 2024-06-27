package com.nhnacademy.bookstore.member.member.service;


import java.time.ZonedDateTime;
import java.util.List;

import com.nhnacademy.bookstore.entity.member.Member;
import com.nhnacademy.bookstore.entity.member.enums.Grade;
import com.nhnacademy.bookstore.entity.member.enums.Status;
import com.nhnacademy.bookstore.member.member.dto.request.UpdateMemberRequest;
import com.nhnacademy.bookstore.purchase.purchase.dto.response.ReadPurchaseResponse;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {
    Member save(Member member);

    Member readById(Long id);

    Member readByEmailAndPassword(String email, String password);

    Member updateMember(Long memberId, UpdateMemberRequest updateMemberRequest);

    void deleteMember(Long memberId);

    Member updateStatus(Long memberId, Status status);

    Member updateGrade(Long memberId, Grade grade);
    Member updateLastLogin(Long memberId, ZonedDateTime lastLogin);

    Page<ReadPurchaseResponse> getPurchasesByMemberId(Long memberId, Pageable pageable);
}
