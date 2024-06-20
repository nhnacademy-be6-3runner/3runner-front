//package com.nhnacademy.bookstore.member.member.service;
//
//import com.nhnacademy.bookstore.entity.member.Member;
//import com.nhnacademy.bookstore.member.member.dto.request.CreateMemberRequest;
//import com.nhnacademy.bookstore.member.member.repository.MemberRepository;
//import com.nhnacademy.bookstore.member.member.service.impl.MemberServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//public class MemberServiceTest2 {
//    @Mock
//    private MemberRepository memberRepository;
//
//    @InjectMocks
//    private MemberServiceImpl memberService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testSaveMember() {
//        CreateMemberRequest createMemberRequest = new CreateMemberRequest("email@naver.com","123456","j","01000000000",0, null);
//        Member member = new Member(createMemberRequest);
//
//        when(memberRepository.save(any(Member.class))).thenReturn(member);
//
//        Member savedMember = memberService.save(member);
//
//        assertThat(savedMember).isNotNull();
//        assertThat(savedMember.getEmail()).isEqualTo("email@naver.com");
//        verify(memberRepository).save(member);
//    }
//
//    @Test
//    public void testFindByIdFound() {
//        CreateMemberRequest createMemberRequest = new CreateMemberRequest("email@naver.com","123456","j","01000000000",0, null);
//        Member member = new Member(createMemberRequest);
//
//        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));
//
//        Member foundMember = memberService.findById(member.getId());
//
//        assertThat(foundMember).isNotNull();
//        assertThat(foundMember.getId()).isEqualTo(member.getId());
//        verify(memberRepository).findById(member.getId());
//    }
//
//    @Test
//    public void testFindByIdNotFound() {
//        Long memberId = 1L;
//        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());
//
//        assertThatThrownBy(() -> memberService.findById(memberId))
//                .isInstanceOf(ResponseStatusException.class)
//                .hasMessageContaining("Member not found");
//
//        verify(memberRepository).findById(memberId);
//    }
//
//    @Test
//    public void testFindByEmailAndPasswordFound() {
//        CreateMemberRequest createMemberRequest = new CreateMemberRequest("email@naver.com","123456","j","01000000000",0, null);
//        Member member = new Member(createMemberRequest);
//
//        when(memberRepository.findByEmailAndPassword(member.getEmail(), member.getPassword())).thenReturn(member);
//
//        Member foundMember = memberService.findByEmailAndPassword(member.getEmail(), member.getPassword());
//
//        assertThat(foundMember).isNotNull();
//        assertThat(foundMember.getEmail()).isEqualTo(member.getEmail());
//        verify(memberRepository).findByEmailAndPassword(member.getEmail(), member.getPassword());
//    }
//
//    @Test
//    public void testFindByEmailAndPasswordNotFound() {
//        String email = "wrong@example.com";
//        String password = "password";
//
//        when(memberRepository.findByEmailAndPassword(email, password)).thenReturn(null);
//
//        assertThatThrownBy(() -> memberService.findByEmailAndPassword(email, password))
//                .isInstanceOf(ResponseStatusException.class)
//                .hasMessageContaining("Member not found");
//
//        verify(memberRepository).findByEmailAndPassword(email, password);
//    }
//}
