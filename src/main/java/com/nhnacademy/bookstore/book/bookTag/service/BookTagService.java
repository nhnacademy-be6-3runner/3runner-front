package com.nhnacademy.bookstore.book.bookTag.service;

import com.nhnacademy.bookstore.book.bookTag.dto.response.ReadBookByTagResponse;
import com.nhnacademy.bookstore.book.bookTag.dto.request.ReadBookIdRequest;
import com.nhnacademy.bookstore.book.bookTag.dto.request.ReadTagRequest;
import com.nhnacademy.bookstore.book.bookTag.dto.response.ReadTagByBookResponse;
import com.nhnacademy.bookstore.entity.tag.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface BookTagService {

    public Page<ReadBookByTagResponse> getBookByTagId(ReadTagRequest tagId, Pageable pageable);

    public Set<ReadTagByBookResponse> getTagByBookId(ReadBookIdRequest bookId);

}
