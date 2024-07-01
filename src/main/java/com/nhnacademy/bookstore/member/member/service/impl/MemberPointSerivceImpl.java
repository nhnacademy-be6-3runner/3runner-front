package com.nhnacademy.bookstore.member.member.service.impl;

import com.nhnacademy.bookstore.entity.member.Member;
import com.nhnacademy.bookstore.member.member.exception.MemberNotExistsException;
import com.nhnacademy.bookstore.member.member.repository.MemberRepository;
import com.nhnacademy.bookstore.member.member.service.MemberPointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class MemberPointSerivceImpl implements MemberPointService {
    private final MemberRepository memberRepository;

    @Override
    public Long updatePoint(Long memberId, Long usePoint) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotExistsException::new);
        Long currentPoint = member.getPoint();
        member.setPoint(currentPoint + usePoint);

        memberRepository.save(member);

        return memberId;
    }

}
