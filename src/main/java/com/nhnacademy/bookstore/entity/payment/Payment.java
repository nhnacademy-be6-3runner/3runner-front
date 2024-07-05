package com.nhnacademy.bookstore.entity.payment;

import com.nhnacademy.bookstore.entity.purchase.Purchase;
import com.nhnacademy.bookstore.entity.payment.enums.PaymentStatus;
import com.nhnacademy.bookstore.entity.paymentType.PaymentType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String tossOrderId;

    private int tossAmount;

    private int tossAmountTasFree;

    private String tossProductDesc;

    @NotNull
    private ZonedDateTime paidAt;

    private PaymentStatus paymentStatus;


    @PrePersist
    protected void onCreate() {
        this.paidAt = ZonedDateTime.now();
    }

    @OneToOne
    private Purchase purchase;

    @ManyToOne
    private PaymentType paymentType;
}
