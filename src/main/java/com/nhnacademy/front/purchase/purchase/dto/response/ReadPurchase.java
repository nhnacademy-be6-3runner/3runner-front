package com.nhnacademy.front.purchase.purchase.dto.response;

import java.time.ZonedDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;

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
						   MemberType memberType,
						   Page<ReadPurchaseBookResponse> readPurchaseBookResponses
) {
}
