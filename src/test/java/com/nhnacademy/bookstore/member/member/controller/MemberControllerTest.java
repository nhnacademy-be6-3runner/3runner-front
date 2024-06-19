package com.nhnacademy.bookstore.member.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.bookstore.entity.member.Member;
import com.nhnacademy.bookstore.member.member.dto.request.UpdateMemberRequest;
import com.nhnacademy.bookstore.member.member.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.ZonedDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class MemberControllerTest {

    MockMvc mockMvc;

    @MockBean
    MemberServiceImpl memberService;

    private Member member;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
        member = new Member();
        member.setId(1L);
        member.setName("UpdatedName");
    }

    @Test
    @DisplayName("멤버 업데이트 테스트")
    public void testMemberUpdate() throws Exception {
        UpdateMemberRequest updateMemberRequest = UpdateMemberRequest.builder()
                .password("newPassword")
                .name("newName")
                .age(25)
                .email("new@example.com")
                .phone("12345678900")
                .birthday(ZonedDateTime.parse("2000-01-01T00:00:00Z"))
                .build();
        Mockito.when(memberService.updateMember(anyString(), any(UpdateMemberRequest.class))).thenReturn(member);

        mockMvc.perform(put("/members")
                        .header("Member-Id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateMemberRequest)))
                        .andExpect(status().isOk());

    }

    @Test
    @DisplayName("멤버 삭제(탈퇴) 테스트")
    public void testMemberDelete() throws Exception {
        Mockito.doNothing().when(memberService).deleteMember(anyString());

        mockMvc.perform(MockMvcRequestBuilders.delete("/members")
                        .header("Member-Id", "1"))
                .andExpect(content().json("{\"header\":{\"resultCode\":204,\"resultMessage\":\"Member deleted\",\"successful\":true}}"));
    }

}
