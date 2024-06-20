package com.nhnacademy.bookstore.purchase.purchase.dto.request;

import com.nhnacademy.bookstore.entity.purchase.enums.PurchaseStatus;
import lombok.Builder;
import lombok.Value;


@Builder
public record UpdatePurchaseRequest(PurchaseStatus purchaseStatus) {
}
