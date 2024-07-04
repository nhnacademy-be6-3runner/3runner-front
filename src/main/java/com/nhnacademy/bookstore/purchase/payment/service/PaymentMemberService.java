package com.nhnacademy.bookstore.purchase.payment.service;

import com.nhnacademy.bookstore.purchase.payment.dto.CreatePaymentMemberRequest;

public interface PaymentMemberService {
    Long payment(CreatePaymentMemberRequest createPaymentMemberRequest);
    Long refund();
    Long partialRefund();
}
