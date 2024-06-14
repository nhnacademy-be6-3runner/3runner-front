package com.nhnacademy.bookstore.entity.purchaseCoupon;

import com.nhnacademy.bookstore.entity.coupon.Coupon;
import com.nhnacademy.bookstore.entity.purchase.Purchase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class PurchaseCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private int discountPrice;


    @ManyToOne
    private Coupon coupon;

    @ManyToOne
    private Purchase purchase;




}
