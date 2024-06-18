package com.nhnacademy.bookstore.book.bookTag.service;

import com.nhnacademy.bookstore.book.bookTag.dto.response.ReadBookByTagResponse;
import com.nhnacademy.bookstore.book.bookTag.dto.request.ReadBookIdRequest;
import com.nhnacademy.bookstore.book.bookTag.dto.request.ReadTagRequest;
import com.nhnacademy.bookstore.book.bookTag.dto.response.ReadTagByBookResponse;
import com.nhnacademy.bookstore.book.bookTag.repository.BookTagRepository;
import com.nhnacademy.bookstore.entity.book.Book;
import com.nhnacademy.bookstore.entity.tag.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;


/**
 * 책에 달린 태그를 검색 하거나 태그로 책을 검색하기 위한 서비스
 *
 * @author 정주혁
 */

@Service
@Transactional
@RequiredArgsConstructor
public class BookTagServiceImpl implements BookTagService {

    private final BookTagRepository bookTagRepository;


    /**
     * 태그가 달린 책들을 불러오기위한 메소드
     *
     * @param tagId 해당 태그가 달린 책의 정보들을 가져오기위한 태그 id
     * @param pageable Page<book>의 pageable 정보
     * @return Page<ReadBookByTagResponse> 해당 태그가 달린 책의 정보들
     *
     */
    public Page<ReadBookByTagResponse> getBookByTagId(ReadTagRequest tagId, Pageable pageable) {
        Page<Book> books = bookTagRepository.findAllBookIdByTagId(tagId.tagId(),pageable);

        return books.map(book-> ReadBookByTagResponse.builder()
                .price(book.getPrice()).author(book.getAuthor()).account(book.getAccount())
                .description(book.getDescription()).title(book.getTitle()).packing(book.isPacking())
                .publishedDate(book.getPublishedDate()).creationDate(book.getCreatedAt()).view_count(book.getView_count())
                .sellingPrice(book.getSellingPrice()).build());
    }

    /**
     * 책에 달린 태그들을 불러오기 위한 메소드
     *
     * @param bookId 해당 책에 달린 태그들을 가져오기위한 책 id
     * @return Set<ReadTagByBookResponse> 해당 책에 달린 태그들
     */
    public Set<ReadTagByBookResponse> getTagByBookId(ReadBookIdRequest bookId) {

        Set<Tag> tags = bookTagRepository.findAllTagIdByBookId(bookId.bookId());

        return tags.stream().map(tag->ReadTagByBookResponse.builder().name(tag.getName()).build()).collect(Collectors.toSet());
    }



}
