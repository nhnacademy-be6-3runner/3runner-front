package com.nhnacademy.bookstore.purchase.payment.service;

import com.nhnacademy.bookstore.purchase.payment.dto.CreatePaymentGuestRequest;

public interface PaymentGuestService {
    Long payment(CreatePaymentGuestRequest createPaymentGuestRequest);
    Long refund();
    Long partialRefund();
}
