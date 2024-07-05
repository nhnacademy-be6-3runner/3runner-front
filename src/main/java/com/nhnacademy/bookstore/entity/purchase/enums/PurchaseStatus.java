package com.nhnacademy.bookstore.entity.purchase.enums;

public enum PurchaseStatus {
    // COMPLETED, DELIVERY_START, DELIVERY_PROGRESS, DELIVERY_COMPLETED,
    //  REFUNDED_REQUEST, REFUNDED_COMPLETED,CONFIRMED;
    PROCESSING, SHIPPED, CONFIRMED, DELIVERY_START, DELIVERY_COMPLETED,
    DELIVERY_PROGRESS, COMPLETED, REFUNDED_REQUEST, REFUNDED_COMPLETED;

    public static PurchaseStatus fromString(String status) {
        if (status == null) {
            throw new IllegalArgumentException("비어있습니다.");
        }
        // Enum values are returned in uppercase, so we convert the input to uppercase
        try {
            return PurchaseStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("없는 값입니다.: " + status, e);
        }
    }
}

