package com.nhnacademy.bookstore.book.bookTag.service;

import com.nhnacademy.bookstore.book.bookTag.dto.request.CreateBookTagRequest;
import com.nhnacademy.bookstore.book.bookTag.dto.response.BookTagResponse;
import com.nhnacademy.bookstore.book.bookTag.dto.response.ReadBookByTagResponse;
import com.nhnacademy.bookstore.book.bookTag.dto.request.ReadBookIdRequest;
import com.nhnacademy.bookstore.book.bookTag.dto.request.ReadTagRequest;
import com.nhnacademy.bookstore.book.bookTag.dto.response.ReadTagByBookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

/**
 * BookTag Read 서비스
 * @author 정주혁
 */
public interface BookTagService {

    public Page<ReadBookByTagResponse> readBookByTagId(ReadTagRequest tagId, Pageable pageable);

    public List<ReadTagByBookResponse> readTagByBookId(ReadBookIdRequest bookId);

    public Long createBookTag(CreateBookTagRequest createBookTagRequest);

}
