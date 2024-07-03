package com.nhnacademy.front.book.book.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhnacademy.front.book.book.dto.request.UserCreateBookRequest;
import com.nhnacademy.front.book.book.dto.response.BookListResponse;
import com.nhnacademy.front.book.book.dto.response.UserReadBookResponse;
import com.nhnacademy.front.book.book.service.BookService;
import com.nhnacademy.front.book.categroy.dto.response.CategoryParentWithChildrenResponse;
import com.nhnacademy.front.book.categroy.dto.response.CategoryResponse;
import com.nhnacademy.front.book.image.service.ImageService;
import com.nhnacademy.front.book.tag.dto.response.ReadTagByBookResponse;
import com.nhnacademy.front.book.tag.dto.response.TagResponse;

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
		String imageName = null;
		if (Objects.nonNull(bookRequest.image())) {
			imageName = imageService.upload(bookRequest.image(), "book");
		}
		bookService.saveBook(bookRequest, imageName);
		return "redirect:/book";
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
	public String readAllBooks(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
		Model model) {
		Page<BookListResponse> bookList = bookService.readAllBooks(page, size);
		model.addAttribute("bookList", bookList);

		return "book/list/book-page-list";
	}

	/**
	 * api 로 북 추가하는 화면
	 * @return api로 북 추가하는 화면
	 */
	@GetMapping("/api/create")
	public String apiCreateBook() {
		return "book/api_book_create";
	}

	/**
	 * api 로 북 추가하는 post 문
	 * @param isbnId 추가할 책의 isbn
	 * @return 추가후 움직일 페이지
	 */
	@PostMapping("/api/create")
	public String apiCreateBook(String isbnId) {
		bookService.saveApiBook(isbnId);
		return "book/api_book_create";
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

	/**
	 *  책 수정 화면 보여주기
	 * @param bookId 보여줄 책의 id
	 * @param model    정보를 넘길 model
	 * @return 수정화면
	 */
	@GetMapping("/update/{bookId}")
	public String updateBook(@PathVariable long bookId, Model model) {
		UserReadBookResponse book = bookService.readBook(bookId);
		model.addAttribute("book", book);
		String publishedDate = book.publishedDate().toString().formatted("yyyy-MM-dd");
		model.addAttribute("publishedDate", publishedDate.substring(0, 10));

		List<TagResponse> requiredTagList = new ArrayList<>();
		for (ReadTagByBookResponse tagResponse : book.tagList()) {
			requiredTagList.add(TagResponse.builder().id(tagResponse.id()).name(tagResponse.name()).build());
		}
		model.addAttribute("requiredTagList", requiredTagList);
		model.addAttribute("requiredCategoryList", parentCategoryGetAllCategoryResponse(book.categoryList()));
		return "book/book_update";
	}

	@PostMapping(value = "/update/{bookId}", consumes = "multipart/form-data")
	public String updateBook(@PathVariable Long bookId, UserCreateBookRequest bookRequest) {
		UserReadBookResponse book = bookService.readBook(bookId);

		String imageName = book.imagePath();
		if (!Objects.equals(bookRequest.image().getOriginalFilename(), "")) {
			imageName = imageService.upload(bookRequest.image(), "book");
		}

		bookService.updateBook(bookId, bookRequest, imageName);

		return "redirect:/book";
	}

	/**
	 * 자식으로 묶여있는 카테고리를 풀어서 내보내기.
	 * @return 모든 카테고리 리스트
	 */
	private List<CategoryResponse> parentCategoryGetAllCategoryResponse(
		List<CategoryParentWithChildrenResponse> parentCategoryList) {
		List<CategoryResponse> categoryResponseList = new ArrayList<>();
		for (CategoryParentWithChildrenResponse categoryResponse : parentCategoryList) {
			categoryResponseList.add(
				CategoryResponse.builder().id(categoryResponse.getId()).name(categoryResponse.getName()).build());
			if (!Objects.nonNull(categoryResponse.getChildrenList()) || categoryResponse.getChildrenList().isEmpty()) {
				continue;
			}
			categoryResponseList.addAll(parentCategoryGetAllCategoryResponse(categoryResponse.getChildrenList()));

		}
		return categoryResponseList;
	}

}
