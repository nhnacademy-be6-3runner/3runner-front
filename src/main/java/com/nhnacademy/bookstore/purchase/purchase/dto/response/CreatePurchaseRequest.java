package com.nhnacademy.bookstore.purchase.purchase.dto.response;

public record CreatePurchaseRequest(
        int deliveryPrice,
        int totalPrice,
        String road) {
}
