package com.nhnacademy.bookstore.entity.refund;

import com.nhnacademy.bookstore.entity.purchase.Purchase;
import com.nhnacademy.bookstore.entity.refund.enums.RefundStatus;
import jakarta.persistence.*;

@Entity
public class Refund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int price;

    private String refund_content;

    private RefundStatus refund_status;

    @ManyToOne
    private Purchase purchase;
}
