package com.nhnacademy.front.book.book.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhnacademy.front.book.book.dto.response.BookListResponse;
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

	/**
	 * 메인 페이지에서 도서 조회하는 메서드입니다.
	 * @param model 데이터를 전달하기 위한 모델 객체
	 * @return 도서 리스트 화면
	 */
	@GetMapping
	public String readLimitBooks(Model model) {
		Page<BookListResponse> bookList = bookService.readLimitBooks(10);
		model.addAttribute("bookList", bookList);

		return "book/book-list";
	}

	/**
	 * 도서 페이지 조회 메서드입니다.
	 * @param page 페이지
	 * @param size 사이즈
	 * @param model 데이터를 전달하기 위한 모델 객체
	 * @return 도서 리스트
	 */
	@GetMapping("/all")
	public String readAllBooks(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
		Model model) {
		Page<BookListResponse> bookList = bookService.readAllBooks(page, size);
		model.addAttribute("bookList", bookList);

		return "book/list/book-page-list";
	}

	/**
	 * 책의 상세 페이지 보기.
	 * @param bookId 책의 id
	 * @param model 페이지의 model
	 * @return 책 상세보기
	 */
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
