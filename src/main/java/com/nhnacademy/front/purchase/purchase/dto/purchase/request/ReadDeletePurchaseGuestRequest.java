package com.nhnacademy.front.purchase.purchase.dto.purchase.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ReadDeletePurchaseGuestRequest(
        @NotNull UUID orderNumber,
        @NotBlank(message = "road is mandatory") String password) {
}
