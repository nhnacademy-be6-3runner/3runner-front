package com.nhnacademy.bookstore.purchase.purchaseBook.dto.request;

import lombok.Builder;

@Builder
public record DeletePurchaseBookRequest(long purchaseBookId) {
}
