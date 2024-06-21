package com.nhnacademy.bookstore.purchase.purchaseBook.exception;

public class NotExistsPurchaseBook extends RuntimeException {
    public NotExistsPurchaseBook() {
        super("해당 주문에서 원하는 책을 찾을수 없습니다.");
    }
}
