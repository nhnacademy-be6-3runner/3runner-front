package com.nhnacademy.bookstore.purchase.bookCart.dto.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RemoveBookCartGuestRequest(
        @NotNull(message = "bookId is mandatory") Long bookId,
        @Min(value = 1, message = "min value is 0") int quantity) {
}
