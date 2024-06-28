package com.nhnacademy.bookstore.purchase.purchaseBook.dto.response;

import com.nhnacademy.bookstore.book.bookTag.dto.response.ReadTagByBookResponse;
import com.nhnacademy.bookstore.book.category.dto.response.CategoryParentWithChildrenResponse;
import com.nhnacademy.bookstore.entity.bookCategory.BookCategory;
import com.nhnacademy.bookstore.entity.bookImage.BookImage;
import com.nhnacademy.bookstore.entity.bookTag.BookTag;
import lombok.Builder;

import java.util.List;

/**
 *  주문-책 조회시 필요한 책 정보들
 *
 *  @author 정주혁
 *
 * @param title
 * @param price
 * @param sellingPrice
 * @param packing
 * @param publisher
 */
@Builder
public record ReadBookByPurchase(String title, int price,
										 String author,
                                         int sellingPrice, boolean packing, String publisher, String bookImage) {
}
