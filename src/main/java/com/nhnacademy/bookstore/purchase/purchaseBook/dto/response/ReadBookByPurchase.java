package com.nhnacademy.bookstore.purchase.purchaseBook.dto.response;

import com.nhnacademy.bookstore.entity.bookCategory.BookCategory;
import com.nhnacademy.bookstore.entity.bookImage.BookImage;
import com.nhnacademy.bookstore.entity.bookTag.BookTag;
import lombok.Builder;

import java.util.List;

@Builder
public record ReadBookByPurchase(String title, String description, int price,
                                         int sellingPrice, boolean packing, String publisher,
                                         List<BookCategory> bookCategories, List<BookTag> bookTags,
                                         List<BookImage> bookImages) {
}
