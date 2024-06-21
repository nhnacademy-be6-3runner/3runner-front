package com.nhnacademy.bookstore.purchase.purchaseBook.exception;

public class DeletePurchaseBookRequestFormException extends RuntimeException {
    public DeletePurchaseBookRequestFormException() {
        super("해당 제거 request가 잘못되었습니다.");
    }
}
