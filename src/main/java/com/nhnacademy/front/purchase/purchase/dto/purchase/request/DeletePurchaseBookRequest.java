package com.nhnacademy.front.purchase.purchase.dto.purchase.request;

import lombok.Builder;


/**
 * 주문-책 삭제 requestDto
 *
 * @author 정주혁
 * @param purchaseBookId
 */
@Builder
public record DeletePurchaseBookRequest(long purchaseBookId) {
}

