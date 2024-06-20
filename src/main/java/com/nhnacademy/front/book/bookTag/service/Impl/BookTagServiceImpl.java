package com.nhnacademy.front.book.bookTag.service.Impl;

import com.nhnacademy.front.book.bookTag.controller.feign.BookTagClient;
import com.nhnacademy.front.book.bookTag.dto.response.BookTagResponse;
import com.nhnacademy.front.book.bookTag.service.BookTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookTagServiceImpl implements BookTagService {

    private final BookTagClient bookTagClient;

    @Override
    public Set<BookTagResponse> readAllBookTags() {

        return bookTagClient.readAllTagSet();
    }
}
