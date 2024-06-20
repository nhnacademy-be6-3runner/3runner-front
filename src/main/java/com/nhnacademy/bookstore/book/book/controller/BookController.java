package com.nhnacademy.bookstore.book.book.controller;

import com.nhnacademy.bookstore.book.book.exception.CreateBookRequestFormException;
import com.nhnacademy.bookstore.book.book.service.BookService;
import com.nhnacademy.bookstore.book.book.service.BookServiceImpl;
import com.nhnacademy.bookstore.entity.book.Book;
import com.nhnacademy.bookstore.entity.book.dto.CreateBookRequest;
import com.nhnacademy.bookstore.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

/**
 * 책 요청 컨트롤러
 * @author 김병우
 */
@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    /**
     * 책 등록 요청 처리
     * @param createBookRequest
     * @param bindingResult
     * @return ApiResponse<Void>
     */
    @PostMapping("/book")
    public ApiResponse<Void> createBook(@Valid CreateBookRequest createBookRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getFieldErrors().forEach(error ->
                    errorMessage.append(error.getField())
                            .append(": ")
                            .append(error.getDefaultMessage())
                            .append("\n")
            );
            throw new CreateBookRequestFormException(errorMessage.toString());
        }

        bookService.createBook(new Book(
                createBookRequest.title(),
                createBookRequest.description(),
                createBookRequest.publishedDate(),
                createBookRequest.price(),
                createBookRequest.quantity(),
                createBookRequest.sellingPrice(),
                createBookRequest.viewCount(),
                createBookRequest.packing(),
                createBookRequest.author(),
                createBookRequest.isbn(),
                createBookRequest.publisher(),
                null,
                null,
                null
        ));

        return new ApiResponse<Void>(new ApiResponse.Header(true, 203));
    }
}

