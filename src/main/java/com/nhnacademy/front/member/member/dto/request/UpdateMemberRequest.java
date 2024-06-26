package com.nhnacademy.front.member.member.dto.request;

import java.time.ZonedDateTime;

import lombok.Builder;

/**
 * The type Update member request.
 *
 * @author 오연수
 */
@Builder
public record UpdateMemberRequest(
    String password,
    String name,
    int age,
    String phone,
    String email,
    ZonedDateTime birthday
){}
