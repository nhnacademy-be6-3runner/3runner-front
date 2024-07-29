package com.nhnacademy.front.book.book.controller;

import com.nhnacademy.front.book.book.dto.request.UserCreateBookRequest;
import com.nhnacademy.front.book.book.dto.response.UserReadBookResponse;
import com.nhnacademy.front.book.book.service.BookService;
import com.nhnacademy.front.book.image.service.ImageService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 도서 컨트롤러입니다.
 *
 * @author 한민기, 김은비
 *
 */
@Slf4j
@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
	private final BookService bookService;
	private final ImageService imageService;

	/**
	 * 책 생성으로 이동하는 메소드 입니다.
	 *
	 * @return 책 생성 url 로 이동
	 */
	@GetMapping("/create")
	public String createBook() {
		return "book/book_create";
	}

	/**
	 * 책 생성 응답 메소드 입니다.
	 *
	 * @param bookRequest 책관련 정보
	 * @return 책 생성완료 후 이동할 url
	 */
	@PostMapping(value = "/create", consumes = "multipart/form-data")
	public String createBook(UserCreateBookRequest bookRequest) {

		log.info(bookRequest.toString());
		String imageName = null;
		if (Objects.nonNull(bookRequest.image())) {
			imageName = imageService.upload(bookRequest.image(), "book");
		}
		bookService.saveBook(bookRequest, imageName);
		return "redirect:/book";
	}

	/**
	 * 책의 상세 페이지 보기.
	 *
	 * @param bookId 책의 id
	 * @param model 페이지의 model
	 * @return 책 상세보기
	 */
	@GetMapping("/{bookId}")
	public String bookDetailView(@PathVariable long bookId, Model model) {
		UserReadBookResponse book = bookService.readBook(bookId);

		model.addAttribute("book", book);

		return "book/detail/book_detail";
	}

}
