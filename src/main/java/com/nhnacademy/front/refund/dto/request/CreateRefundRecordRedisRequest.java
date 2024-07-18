package com.nhnacademy.front.refund.dto.request;

import com.nhnacademy.front.purchase.purchase.dto.purchase.response.ReadBookByPurchase;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreateRefundRecordRedisRequest(
											 @NotNull @Min(0) int quantity,
											 @NotNull @Min(0) int price,
											 ReadBookByPurchase readBookByPurchase) {
}
