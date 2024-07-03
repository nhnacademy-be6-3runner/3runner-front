package com.nhnacademy.front.book.book.controller;

import com.nhnacademy.front.book.book.dto.request.UserCreateBookRequest;
import com.nhnacademy.front.book.book.dto.response.BookListResponse;
import com.nhnacademy.front.book.book.dto.response.UserReadBookResponse;
import com.nhnacademy.front.book.book.service.BookService;
import com.nhnacademy.front.book.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
	public String readAllBooks(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Model model) {
		Page<BookListResponse> bookList = bookService.readAllBooks(page, size);
		model.addAttribute("bookList", bookList);

		return "book/list/book-page-list";
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
