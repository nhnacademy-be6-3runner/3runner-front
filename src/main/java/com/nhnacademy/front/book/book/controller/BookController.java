package com.nhnacademy.front.book.book.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhnacademy.front.book.book.dto.request.UserCreateBookRequest;
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
	public String bookDetailView(@PathVariable String bookId, Model model) {

		return "book/book_detail";
	}

}
