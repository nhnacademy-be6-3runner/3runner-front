package com.nhnacademy.bookstore.member.member.repository;

import com.nhnacademy.bookstore.entity.member.Member;
import com.nhnacademy.bookstore.member.member.dto.request.CreateMemberRequest;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberRepositoryTest2 {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private MemberRepository memberRepository;
    @BeforeEach
    void setUp() {
        entityManager.clear();
    }
    @Test
    void testSaveAndFindByEmailAndPassword(){
        CreateMemberRequest createMemberRequest = new CreateMemberRequest("email@naver.com","123456","j","01000000000",0, null);
        Member member = new Member(createMemberRequest);
        Member savedMember = memberRepository.save(member);
        Member foundMember = memberRepository.findByEmailAndPassword(member.getEmail(), member.getPassword());
        assertThat(foundMember).isEqualTo(savedMember);
    }
}
