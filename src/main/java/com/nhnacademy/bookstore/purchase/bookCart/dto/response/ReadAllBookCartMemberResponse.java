package com.nhnacademy.bookstore.purchase.bookCart.dto.response;


import lombok.Builder;

@Builder
public record ReadAllBookCartMemberResponse(int quantity, ReadBookCartBook book) {
}
