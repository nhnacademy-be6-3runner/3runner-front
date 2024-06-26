package com.nhnacademy.bookstore.book.booktag.service;

import com.nhnacademy.bookstore.book.booktag.dto.request.CreateBookTagRequest;
import com.nhnacademy.bookstore.book.booktag.dto.response.ReadBookByTagResponse;
import com.nhnacademy.bookstore.book.booktag.dto.request.ReadBookIdRequest;
import com.nhnacademy.bookstore.book.booktag.dto.request.ReadTagRequest;
import com.nhnacademy.bookstore.book.booktag.dto.response.ReadTagByBookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * BookTag Read 서비스
 * @author 정주혁
 */
public interface BookTagService {

    public Page<ReadBookByTagResponse> readBookByTagId(ReadTagRequest tagId, Pageable pageable);

    public List<ReadTagByBookResponse> readTagByBookId(ReadBookIdRequest bookId);

    public Long createBookTag(CreateBookTagRequest createBookTagRequest);

}
