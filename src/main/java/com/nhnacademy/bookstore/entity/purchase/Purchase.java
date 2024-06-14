package com.nhnacademy.bookstore.entity.purchase;

import com.nhnacademy.bookstore.entity.pointRecord.PointRecord;
import com.nhnacademy.bookstore.entity.purchase.enums.MemberType;
import com.nhnacademy.bookstore.entity.purchase.enums.PurchaseStatus;
import com.nhnacademy.bookstore.entity.purchaseBook.PurchaseBook;
import com.nhnacademy.bookstore.entity.purchaseCoupon.PurchaseCoupon;
import com.nhnacademy.bookstore.entity.member.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(unique = true)
    private UUID orderNumber;

    @NotNull
    private PurchaseStatus status;

    @NotNull
    private int deliveryPrice;

    @NotNull
    private int totalPrice;

    @NotNull
    private ZonedDateTime createdAt;

    @NotNull
    private String road;

    private String password;

    @NotNull
    private MemberType memberType;

    @ManyToOne
    private Member member;

    @OneToOne
    PointRecord pointRecord;


    //연결
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PurchaseBook> purchaseBookSet = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PurchaseCoupon> purchaseCouponSet = new HashSet<>();
}
