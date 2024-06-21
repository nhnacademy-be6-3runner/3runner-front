package com.nhnacademy.bookstore.purchase.purchaseBook.exception;

public class ReadPurchaseBookRequestFormException extends RuntimeException {
    public ReadPurchaseBookRequestFormException() {
        super("해당 request가 잘못 되었습니다.");
    }
}
