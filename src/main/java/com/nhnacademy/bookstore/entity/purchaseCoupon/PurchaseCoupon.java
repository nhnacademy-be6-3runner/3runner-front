package com.nhnacademy.bookstore.entity.purchaseCoupon;

import com.nhnacademy.bookstore.entity.coupon.Coupon;
import com.nhnacademy.bookstore.entity.purchase.Purchase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter@Setter
public class PurchaseCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private int discountPrice;

    private String status;

    @ManyToOne
    private Coupon coupon;

    @ManyToOne
    private Purchase purchase;

    public PurchaseCoupon(int discountPrice, String status, Coupon coupon, Purchase purchase) {
        this.discountPrice = discountPrice;
        this.status = status;
        this.coupon = coupon;
        this.purchase = purchase;
    }
}
