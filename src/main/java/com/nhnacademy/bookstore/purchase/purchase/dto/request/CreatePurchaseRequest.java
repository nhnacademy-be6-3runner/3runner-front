package com.nhnacademy.bookstore.purchase.purchase.dto.request;

public record CreatePurchaseRequest(
        int deliveryPrice,
        int totalPrice,
        String road) {
}
