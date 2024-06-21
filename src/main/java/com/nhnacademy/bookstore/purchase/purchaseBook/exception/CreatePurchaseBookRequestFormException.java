package com.nhnacademy.bookstore.purchase.purchaseBook.exception;

public class CreatePurchaseBookRequestFormException extends RuntimeException {
    public CreatePurchaseBookRequestFormException() {
        super("해당 생성 request가 잘못되었습니다.");
    }
}
