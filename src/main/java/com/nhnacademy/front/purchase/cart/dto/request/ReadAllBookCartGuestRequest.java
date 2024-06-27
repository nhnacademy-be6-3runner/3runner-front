package com.nhnacademy.front.purchase.cart.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ReadAllBookCartGuestRequest(
        Long cartId) {
}
