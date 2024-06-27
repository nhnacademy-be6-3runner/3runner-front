package com.nhnacademy.bookstore.book.book.controller;

import com.nhnacademy.bookstore.book.book.dto.request.CreateBookRequest;
import com.nhnacademy.bookstore.book.book.dto.response.BookListResponse;
import com.nhnacademy.bookstore.book.book.dto.response.ReadBookResponse;
import com.nhnacademy.bookstore.book.book.exception.CreateBookRequestFormException;
import com.nhnacademy.bookstore.book.book.service.BookService;
import com.nhnacademy.bookstore.book.bookCartegory.dto.request.CreateBookCategoryRequest;
import com.nhnacademy.bookstore.book.bookCartegory.service.BookCategoryService;
import com.nhnacademy.bookstore.book.bookImage.service.BookImageService;
import com.nhnacademy.bookstore.book.bookTag.dto.request.CreateBookTagListRequest;
import com.nhnacademy.bookstore.book.bookTag.service.BookTagService;
import com.nhnacademy.bookstore.entity.bookImage.enums.BookImageType;
import com.nhnacademy.bookstore.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * 책 요청 컨트롤러.
 *
 * @author 김병우
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/bookstore/books")
public class BookController {
    private final BookService bookService;
    private final BookImageService bookImageService;
    private final BookTagService bookTagService;
    private final BookCategoryService bookCategoryService;

    /**
     * 책 등록 요청 처리.
     *
     * @param createBookRequest request form
     * @param bindingResult     binding result
     * @return ApiResponse<>
     */
    @PostMapping
    public ApiResponse<Void> createBook(@Valid @RequestBody CreateBookRequest createBookRequest,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CreateBookRequestFormException(bindingResult.getFieldErrors().toString());
        }
        long bookId = bookService.createBook(createBookRequest);
        bookCategoryService.createBookCategory(
                CreateBookCategoryRequest.builder().bookId(bookId).categoryIds(createBookRequest.categoryIds()).build());
        bookTagService.createBookTag(
                CreateBookTagListRequest.builder().bookId(bookId).tagIdList(createBookRequest.tagIds()).build());
        bookImageService.createBookImage(createBookRequest.imageList(), bookId, BookImageType.DESCRIPTION);
        if (!Objects.isNull(createBookRequest.imageName())) {
            bookImageService.createBookImage(List.of(createBookRequest.imageName()), bookId, BookImageType.MAIN);
        }

        return new ApiResponse<>(new ApiResponse.Header(true, 201));
    }

    @GetMapping("/{bookId}")
    public ApiResponse<ReadBookResponse> readBook(@PathVariable("bookId") Long bookId) {
        ReadBookResponse book = bookService.readBookById(bookId);

        return new ApiResponse<ReadBookResponse>(
                new ApiResponse.Header(true, 200),
                new ApiResponse.Body<ReadBookResponse>(book)
        );
    }

    @GetMapping
    public ApiResponse<Page<BookListResponse>> readAllBooks(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BookListResponse> bookList = bookService.readAllBooks(pageable);
        return new ApiResponse<>(
                new ApiResponse.Header(true, 200),
                new ApiResponse.Body<>(bookList)
        );
    }
}

