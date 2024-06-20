package com.nhnacademy.front.book.book.controller;

import com.nhnacademy.front.book.book.dto.request.UserCreateBookRequest;
import com.nhnacademy.front.book.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/create")
    public String createBook(){
        return "book/book_create";
    }

    @PostMapping("/create")
    public String createBook(UserCreateBookRequest bookRequest, Model model){

        bookService.saveBook(bookRequest);

        return "book/book_create";
    }
}
