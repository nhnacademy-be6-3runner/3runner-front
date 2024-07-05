package com.nhnacademy.bookstore.purchase.payment.service.impl;

import com.nhnacademy.bookstore.member.pointRecord.service.PointRecordService;
import com.nhnacademy.bookstore.purchase.bookCart.dto.request.ReadAllBookCartMemberRequest;
import com.nhnacademy.bookstore.purchase.bookCart.dto.response.ReadAllBookCartMemberResponse;
import com.nhnacademy.bookstore.purchase.bookCart.service.BookCartMemberService;
import com.nhnacademy.bookstore.purchase.coupon.service.CouponMemberService;
import com.nhnacademy.bookstore.purchase.payment.dto.CreatePaymentMemberRequest;
import com.nhnacademy.bookstore.purchase.payment.service.PaymentMemberService;
import com.nhnacademy.bookstore.purchase.purchase.dto.request.CreatePurchaseRequest;
import com.nhnacademy.bookstore.purchase.purchase.service.PurchaseMemberService;
import com.nhnacademy.bookstore.purchase.purchaseBook.dto.request.CreatePurchaseBookRequest;
import com.nhnacademy.bookstore.purchase.purchaseBook.service.PurchaseBookService;
import com.nhnacademy.bookstore.purchase.purchaseCoupon.service.PurchaseCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class PaymentMemberServiceImpl implements PaymentMemberService {
    private final BookCartMemberService bookCartMemberService;
    private final PurchaseMemberService purchaseMemberService;
    private final PurchaseBookService purchaseBookService;
    private final PointRecordService pointRecordService;
    private final PurchaseCouponService purchaseCouponService;
    private final CouponMemberService couponMemberService;

    @Override
    public Long payment(CreatePaymentMemberRequest createPaymentMemberRequest) {
        Long purchaseId = purchaseMemberService.createPurchase(
                CreatePurchaseRequest.builder()
                        .orderId(createPaymentMemberRequest.orderId())
                        .road(createPaymentMemberRequest.road())
                        .totalPrice(createPaymentMemberRequest.amount())
                        .shippingDate(createPaymentMemberRequest.shippingDate())
                        .isPacking(createPaymentMemberRequest.isPacking())
                        .deliveryPrice(3000).build(),
                createPaymentMemberRequest.memberId()
        );

        List<ReadAllBookCartMemberResponse> bookCartMemberResponseList = bookCartMemberService.readAllCartMember(ReadAllBookCartMemberRequest.builder().userId(createPaymentMemberRequest.memberId()).build());

        for (ReadAllBookCartMemberResponse bookCartMemberResponse : bookCartMemberResponseList) {
            purchaseBookService.createPurchaseBook(
                    CreatePurchaseBookRequest.builder()
                            .bookId(bookCartMemberResponse.bookId())
                            .purchaseId(purchaseId)
                            .price(bookCartMemberResponse.price())
                            .quantity(bookCartMemberResponse.quantity())
                            .build()
            );
        }

        //포인트 적립
        pointRecordService.save(
                (long)(createPaymentMemberRequest.amount()*0.05),
                createPaymentMemberRequest.orderId() + ":주문 적립",
                createPaymentMemberRequest.memberId(),
                purchaseId
        );

        //포인트 사용 0
        if (createPaymentMemberRequest.discountedPoint() != 0) {
            pointRecordService.save(
                    -1L * createPaymentMemberRequest.discountedPoint(),
                    createPaymentMemberRequest.orderId() + ":주문 사용",
                    createPaymentMemberRequest.memberId(),
                    purchaseId
            );
        }

        //쿠폰 사용
        Long couponId = couponMemberService
                .readCoupon(createPaymentMemberRequest.couponFormId());

        couponMemberService.useCoupons(
                createPaymentMemberRequest.couponFormId(),
                createPaymentMemberRequest.memberId()
        );

        purchaseCouponService.create(
                purchaseId,
                createPaymentMemberRequest.couponFormId(),
                createPaymentMemberRequest.discountedPrice()
        );

        return purchaseId;
    }

    @Override
    public Long refund() {
        return 0L;
    }

    @Override
    public Long partialRefund() {
        return 0L;
    }
}
