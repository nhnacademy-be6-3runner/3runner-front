package com.nhnacademy.bookstore.purchase.bookCart.dto.response;

import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Value;
@Builder
public record ReadBookCartGuestResponse(
        Long bookId,
        Long cartId,
        int quantity,
        ZonedDateTime createdAt
) { }
