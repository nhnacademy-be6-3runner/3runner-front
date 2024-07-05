package com.nhnacademy.front.purchase.purchase.dto.purchase.response;

import lombok.Builder;

/**
 * 주문-책 조회시 responseDto
 *
 * @author 정주혁
 *
 * @param readBookByPurchase
 * @param quantity
 * @param price
 */
@Builder
public record ReadPurchaseBookResponse(ReadBookByPurchase readBookByPurchase, int id, int quantity, int price) {
}
