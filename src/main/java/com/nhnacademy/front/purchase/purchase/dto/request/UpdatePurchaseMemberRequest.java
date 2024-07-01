package com.nhnacademy.front.purchase.purchase.dto.request;

import com.nhnacademy.front.entity.purchase.enums.PurchaseStatus;
import lombok.Builder;


@Builder
public record UpdatePurchaseMemberRequest(PurchaseStatus purchaseStatus) {
}
