package com.nhnacademy.bookstore.book.book.controller;

import com.nhnacademy.bookstore.book.book.dto.response.ReadBookResponse;
import com.nhnacademy.bookstore.book.book.exception.CreateBookRequestFormException;
import com.nhnacademy.bookstore.book.book.service.BookService;
import com.nhnacademy.bookstore.entity.book.Book;
import com.nhnacademy.bookstore.book.book.dto.request.CreateBookRequest;
import com.nhnacademy.bookstore.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 책 요청 컨트롤러.
 *
 * @author 김병우
 */
@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    /**
     * 책 등록 요청 처리.
     *
     * @param createBookRequest request form
     * @param bindingResult binding result
     * @return ApiResponse<>
     */
    @PostMapping("/book")
    public ApiResponse<Void> createBook(@Valid CreateBookRequest createBookRequest,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CreateBookRequestFormException(bindingResult.getFieldErrors().toString());
        }

        // controller 쪽은 entity X -> OSIV(Open session in view) view에 오기전에 닫아버리기? -> service에서 처리
        bookService.createBook(createBookRequest);
        //TODO 북 카테고리 서비스로 추가
        //TODO 북 태그 서비스로 추가

        return new ApiResponse<Void>(new ApiResponse.Header(true, 201));
    }

    @GetMapping("/book/{bookId}")
    public ApiResponse<ReadBookResponse> readBook(@PathVariable("bookId") Long bookId) {
        ReadBookResponse book = bookService.readBookById(bookId);

        return new ApiResponse<ReadBookResponse>(
                new ApiResponse.Header(true, 200),
                new ApiResponse.Body<ReadBookResponse>(book)
        );
    }
}

