package com.nhnacademy.bookstore.purchase.purchase.dto.request;

import com.nhnacademy.bookstore.entity.purchase.enums.PurchaseStatus;
import lombok.Value;

public record UpdatePurchaseRequest(PurchaseStatus purchaseStatus) {
}
