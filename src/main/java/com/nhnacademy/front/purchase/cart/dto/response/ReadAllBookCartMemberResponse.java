package com.nhnacademy.front.purchase.cart.dto.response;


import lombok.Builder;

@Builder
public record ReadAllBookCartMemberResponse(int quantity, ReadBookCartBook book) {
}
