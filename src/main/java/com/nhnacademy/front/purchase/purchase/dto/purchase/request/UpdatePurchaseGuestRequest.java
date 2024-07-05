package com.nhnacademy.front.purchase.purchase.dto.purchase.request;

import com.nhnacademy.front.entity.purchase.enums.PurchaseStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;


@Builder
public record UpdatePurchaseGuestRequest(PurchaseStatus purchaseStatus,
                                         @NotNull(message = "orderNumber is mandatory") UUID orderNumber,
                                         String password) {
}
