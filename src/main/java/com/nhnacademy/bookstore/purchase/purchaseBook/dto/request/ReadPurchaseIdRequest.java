package com.nhnacademy.bookstore.purchase.purchaseBook.dto.request;

import lombok.Builder;


/**
 * 주문-책 조회 requestDto
 *
 * @author 정주혁
 *
 * @param purchaseId
 */
@Builder
public record ReadPurchaseIdRequest(Long purchaseId) {
}
