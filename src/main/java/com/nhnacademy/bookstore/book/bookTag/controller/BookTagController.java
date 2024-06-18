package com.nhnacademy.bookstore.book.bookTag.controller;

import com.nhnacademy.bookstore.book.bookTag.dto.request.ReadBookIdRequest;
import com.nhnacademy.bookstore.book.bookTag.dto.response.ReadBookByTagResponse;
import com.nhnacademy.bookstore.book.bookTag.dto.request.ReadTagRequest;
import com.nhnacademy.bookstore.book.bookTag.dto.response.ReadTagByBookResponse;
import com.nhnacademy.bookstore.book.bookTag.service.BookTagService;
import com.nhnacademy.bookstore.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * @author 정주혁
 *
 * booktagController
 */

@RestController
@RequestMapping
public class  BookTagController {
    @Autowired
    BookTagService bookTagService;


    /**
     * 태그값으로 책들을 가져오기 위한 메소드
     *
     * @param tagId 해당 태그가 달린 책을 가져오기 위한 태그 id
     * @param page Pageable의 기본 페이지
     * @param size Pageable의 사이즈
     * @param sort Pageable의 정렬 방식
     * @return ApiResponse< Page<ReadBookByTagResponse>> 커스터마이징 한 헤더와 불러온 해당 태그가 달린 책들로 이루어진 바디를 합친 실행 값
     */
    @GetMapping("/bookTag/{tagId}")
    public ApiResponse< Page<ReadBookByTagResponse>> getBookByTagId(@PathVariable ReadTagRequest tagId,
                                                                                         @RequestParam("page") int page,
                                                                                         @RequestParam("size") int size,
                                                                                         @RequestParam(required = false) String sort
                                                                     ) {
        Pageable pageable;
        if(tagId == null) {
            return ApiResponse.fail(HttpStatus.BAD_REQUEST.value(), "tagId is required");
        }
        if(sort != null && !sort.isEmpty()) {
            pageable = PageRequest.of(page,size, Sort.by(sort));
        }
        else{
            pageable = PageRequest.of(page,size);
        }

        Page<ReadBookByTagResponse> bookByTagResponsePage = bookTagService.getBookByTagId(tagId,pageable);

        return ApiResponse.success(bookByTagResponsePage);
    }


    /**
     * 책에 달린 태그들을 가져오기 위한 메소드
     * @param bookId 책에 달린 태그들을 가져오기 위한 책 id
     * @return ApiResponse< Page<ReadBookByTagResponse>> 커스터마이징 한 헤더와 불러온 해당 책에 달린 태그들로 이루어진 바디를 합친 실행 값
     */
    @GetMapping("/bookTag/{bookId}")
    public ApiResponse<Set<ReadTagByBookResponse>> getTagByBookId(@PathVariable ReadBookIdRequest bookId) {
        if(bookId == null) {
            return ApiResponse.fail(HttpStatus.BAD_REQUEST.value(), "tagId is required");

        }
        Set<ReadTagByBookResponse> tags = bookTagService.getTagByBookId(bookId);

        return ApiResponse.success(tags);

    }}
