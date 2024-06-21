package com.nhnacademy.bookstore.purchase.purchaseBook.dto.response;

import lombok.Builder;

@Builder
public record ReadPurchaseBookResponse(ReadBookByPurchase readBookByPurchase, int quantity, int price) {
}
