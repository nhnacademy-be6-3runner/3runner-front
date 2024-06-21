package com.nhnacademy.bookstore.purchase.purchaseBook.dto.request;

import com.nhnacademy.bookstore.entity.book.Book;
import com.nhnacademy.bookstore.entity.purchase.Purchase;
import lombok.Builder;

@Builder
public record CreatePurchaseBookRequest(long bookId, int quantity, int price, long purchaseId) {
}
