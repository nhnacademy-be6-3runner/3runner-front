package com.nhnacademy.bookstore.purchase.refundRecord.dto.response;

import com.nhnacademy.bookstore.purchase.purchaseBook.dto.response.ReadBookByPurchase;

import lombok.Builder;

@Builder
public record ReadRefundRecordResponse(long id, int quantity, int price, ReadBookByPurchase readBookByPurchase) {
}
