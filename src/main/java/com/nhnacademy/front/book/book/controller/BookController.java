package com.nhnacademy.front.book.book.controller;

import com.nhnacademy.front.book.book.dto.request.UserCreateBookRequest;
import com.nhnacademy.front.book.book.dto.response.BookListResponse;
import com.nhnacademy.front.book.book.service.BookService;
import com.nhnacademy.front.book.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;

import com.nhnacademy.front.book.book.dto.request.UserCreateBookRequest;
import com.nhnacademy.front.book.book.dto.response.UserReadBookResponse;
import com.nhnacademy.front.book.book.service.BookService;
import com.nhnacademy.front.book.image.service.ImageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
	private final BookService bookService;
	private final ImageService imageService;

	@GetMapping("/create")
	public String createBook() {
		return "book/book_create";
	}

	@PostMapping(value = "/create", consumes = "multipart/form-data")
	public String createBook(UserCreateBookRequest bookRequest, Model model) {

		log.info(bookRequest.toString());
		String imageName = imageService.upload(bookRequest.image(), "book");
		bookService.saveBook(bookRequest, imageName);

		return "book/book_create";
	}

    @GetMapping
    public String readLimitBooks(Model model) {
        Page<BookListResponse> bookList = bookService.readLimitBooks(10);
        model.addAttribute("bookList", bookList);

        return "book/book-list";
    }

	@GetMapping("/api/create")
	public String apiCreateBook() {
		return "book/api_book_create";
	}

	@PostMapping("/api/create")
	public String apiCreateBook(String isbnId, Model model) {
		bookService.saveApiBook(isbnId);
		return "book/api_book_create";
	}

	@GetMapping("/{bookId}")
	public String bookDetailView(@PathVariable long bookId, Model model) {
		UserReadBookResponse book = bookService.readBook(bookId);

		model.addAttribute("book", book);

		log.info("description : {}", book.description());
		
		model.addAttribute("rating", 4.9);
		model.addAttribute("reviewCount", 10);

		return "book/book_detail";
	}
}
