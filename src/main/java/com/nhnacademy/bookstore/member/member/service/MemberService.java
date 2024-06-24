package com.nhnacademy.bookstore.member.member.service;

import org.springframework.stereotype.Service;

import com.nhnacademy.bookstore.entity.member.Member;
import com.nhnacademy.bookstore.entity.member.enums.Grade;
import com.nhnacademy.bookstore.entity.member.enums.Status;
import com.nhnacademy.bookstore.member.member.dto.request.UpdateMemberRequest;

@Service
public interface MemberService {
	Member save(Member member);

	Member readById(Long id);

	Member readByEmailAndPassword(String email, String password);

	Member readByEmail(String email);

	Member updateMember(Long memberId, UpdateMemberRequest updateMemberRequest);

	void deleteMember(Long memberId);

	Member updateStatus(Long memberId, Status status);

	Member updateGrade(Long memberId, Grade grade);
}
