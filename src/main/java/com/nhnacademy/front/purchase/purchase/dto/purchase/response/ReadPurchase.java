package com.nhnacademy.front.purchase.purchase.dto.purchase.response;

import com.nhnacademy.front.entity.purchase.enums.MemberType;
import com.nhnacademy.front.entity.purchase.enums.PurchaseStatus;

import lombok.Builder;

@Builder
public record ReadPurchase(long id,
						   String orderNumber,
						   PurchaseStatus status,
						   int deliveryPrice,
						   int totalPrice,
						   String createdAt,
						   String road , 			// 주소
						   String password,
						   MemberType memberType
) {
}
