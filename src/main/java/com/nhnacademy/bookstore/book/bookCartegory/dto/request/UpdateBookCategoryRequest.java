package com.nhnacademy.bookstore.book.bookCartegory.dto.request;

import lombok.Builder;

/**
 * book id 를 통해서 category id 변경
 * @author 김은비
 */
@Builder
public record UpdateBookCategoryRequest(long bookId, long categoryId) {
}
