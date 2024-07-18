package com.nhnacademy.front.purchase.purchase.dto.member.request;

import java.time.ZonedDateTime;

import lombok.Builder;

/**
 * The type Update member request.
 *
 * @author 오연수
 */
@Builder
public record UpdateMemberRequest(
    String name,
    int age,
    String phone,
    String birthday
){}
