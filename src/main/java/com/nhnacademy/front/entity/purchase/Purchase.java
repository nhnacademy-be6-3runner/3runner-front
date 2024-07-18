package com.nhnacademy.front.entity.purchase;

import com.nhnacademy.front.entity.member.Member;
import com.nhnacademy.front.entity.pointRecord.PointRecord;
import com.nhnacademy.front.entity.purchase.enums.MemberType;
import com.nhnacademy.front.entity.purchase.enums.PurchaseStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter@Setter
public class Purchase {
    private long id;
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

    private Member member;

    PointRecord pointRecord;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Purchase purchase)) return false;
        return Objects.equals(getOrderNumber(), purchase.getOrderNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getOrderNumber());
    }

}
