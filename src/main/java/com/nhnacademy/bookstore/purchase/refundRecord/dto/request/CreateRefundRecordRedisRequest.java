package com.nhnacademy.bookstore.purchase.refundRecord.dto.request;

import com.nhnacademy.bookstore.purchase.purchaseBook.dto.response.ReadBookByPurchase;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreateRefundRecordRedisRequest(
											 @NotNull @Min(0) int quantity,
											 @NotNull @Min(0) int price,
											 ReadBookByPurchase readBookByPurchase) {
}
