package com.nhnacademy.bookstore.purchase.bookCart.dto.response;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.key.ZonedDateTimeKeyDeserializer;
import lombok.*;

/**
 * 카트 응답 폼
 *
 * @author 김병우
 * @param bookId 도서 아이디
 * @param cartId 장바구니 아이디
 * @param bookCartId 도서장바구니 아이디
 * @param quantity 수량
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReadBookCartGuestResponse{
    private Long bookId;
    private Long cartId;
    private Long bookCartId;
    private int quantity;
}
