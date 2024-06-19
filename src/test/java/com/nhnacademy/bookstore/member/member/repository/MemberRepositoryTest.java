package com.nhnacademy.bookstore.member.member.repository;

import com.nhnacademy.bookstore.entity.member.Member;
import com.nhnacademy.bookstore.entity.member.enums.Grade;
import com.nhnacademy.bookstore.entity.member.enums.Status;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.ZonedDateTime;

@DataJpaTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("멤버 저장 테스트")
    public void saveTest() {
        Member member = Member.builder()
                .name("test-name")
                .password("test-password")
                .point(6000L)
                .age(100)
                .phone("01012345678")
                .birthday(ZonedDateTime.parse("2000-01-01T00:00:00Z"))
                .grade(Grade.General)
                .status(Status.Active)
                .created_at(ZonedDateTime.now())
                .email("test@email.com")
                .build();

        Member savedMember = memberRepository.save(member);

        Assertions.assertNotNull(savedMember.getId());
        Assertions.assertEquals("test-name", member.getName());
        Assertions.assertEquals("test-password", member.getPassword());
        Assertions.assertEquals("test@email.com", member.getEmail());
    }

    @Test
    @DisplayName("멤버 아이디로 멤버 조회 테스트")
    public void findByIdTest() {
        Member member = Member.builder()
                .name("test-name")
                .password("test-password")
                .point(6000L)
                .age(100)
                .phone("01012345678")
                .birthday(ZonedDateTime.parse("2000-01-01T00:00:00Z"))
                .grade(Grade.General)
                .status(Status.Active)
                .created_at(ZonedDateTime.now())
                .email("test@email.com")
                .build();
        Member savedMember = memberRepository.save(member);

        Member foundMember = memberRepository.findById(savedMember.getId()).get();

        Assertions.assertNotNull(foundMember);

    }

    @Test
    @DisplayName("멤버 업데이트 테스트")
    public void updateTest() {
        Member member = Member.builder()
                .name("test-name")
                .password("test-password")
                .point(6000L)
                .age(100)
                .phone("01012345678")
                .birthday(ZonedDateTime.parse("2000-01-01T00:00:00Z"))
                .grade(Grade.General)
                .status(Status.Active)
                .created_at(ZonedDateTime.now())
                .email("test@email.com")
                .build();
        Member savedMember = memberRepository.save(member);
        Member foundMember = memberRepository.findById(savedMember.getId()).get();

        foundMember.setName("new-name");
        foundMember.setPassword("updated-password");
        foundMember.setEmail("update@email.com");
        memberRepository.save(foundMember);
        entityManager.flush();

        Assertions.assertEquals(savedMember.getId(), foundMember.getId());
        Assertions.assertEquals("new-name", foundMember.getName());
        Assertions.assertEquals("updated-password", foundMember.getPassword());
        Assertions.assertEquals("update@email.com", foundMember.getEmail());
    }
}
