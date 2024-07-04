package com.nhnacademy.front.purchase.purchase.dto.purchase.response;

import com.nhnacademy.front.entity.purchase.enums.MemberType;
import com.nhnacademy.front.entity.purchase.enums.PurchaseStatus;
import lombok.Builder;

import java.time.ZonedDateTime;
import java.util.UUID;

@Builder
public record ReadPurchaseResponse(long id,
								   UUID orderNumber,
								   PurchaseStatus status,
								   int deliveryPrice,
								   int totalPrice,
								   ZonedDateTime createdAt,
								   String road,
								   String password,
								   MemberType memberType,
								   ZonedDateTime shippingDate,
								   boolean isPacking) {

}
