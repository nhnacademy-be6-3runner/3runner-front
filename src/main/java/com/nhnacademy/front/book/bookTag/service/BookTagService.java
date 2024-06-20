package com.nhnacademy.front.book.bookTag.service;

import com.nhnacademy.front.book.bookTag.dto.response.BookTagResponse;

import java.util.Set;

public interface BookTagService {
    Set<BookTagResponse> readAllBookTags();
}
