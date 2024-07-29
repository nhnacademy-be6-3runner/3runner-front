package com.nhnacademy.front.book.book.controller;

import com.nhnacademy.front.book.book.dto.request.UserCreateBookRequest;
import com.nhnacademy.front.book.book.dto.response.UserReadBookResponse;
import com.nhnacademy.front.book.book.service.BookService;
import com.nhnacademy.front.book.categroy.dto.response.CategoryParentWithChildrenResponse;
import com.nhnacademy.front.book.categroy.dto.response.CategoryResponse;
import com.nhnacademy.front.book.image.service.ImageService;
import com.nhnacademy.front.book.tag.dto.response.ReadTagByBookResponse;
import com.nhnacademy.front.book.tag.dto.response.TagResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 도서 관리 컨트롤러 입니다.
 *
 * @author 한민기
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class BookManagementController {

	private final BookService bookService;
	private final ImageService imageService;

	/**
	 * 책 등록 화면으로 가는 메소드 입니다.
	 *
	 * @return 책 등록 화면
	 */
	@GetMapping("/publisher/book/create")
	public String createBook() {
		return "book/book_create";
	}

	/**
	 * 책 등록을 처리하는 메소드 입니다.
	 *
	 * @param bookRequest 등록할 책의 정보
	 * @return 책 등록
	 */
	@PostMapping(value = "/publisher/book/create", consumes = "multipart/form-data")
	public String createBook(UserCreateBookRequest bookRequest) {

		String imageName = null;
		if (Objects.nonNull(bookRequest.image())) {
			imageName = imageService.upload(bookRequest.image(), "book");
		}
		bookService.saveBook(bookRequest, imageName);
		return "redirect:/book";
	}

	/**
	 * Isbn 으로 북 추가하는 화면으로 가는 메소드 입니다.
	 *
	 * @return Isbn 으로 책 등록하는 화면
	 */
	@GetMapping("/admin/book/api/create")
	public String apiCreateBook() {
		return "book/api_book_create";
	}

	/**
	 * api 로 북 추가하는 post 문.
	 *
	 * @param isbnId 추가할 책의 isbn
	 * @return 추가후 움직일 페이지
	 */
	@PostMapping("/admin/book/api/create")
	public String apiCreateBook(String isbnId) {
		bookService.saveApiBook(isbnId);
		return "book/api_book_create";
	}

	/**
	 *  책 수정 화면 보여주기
	 * @param bookId 보여줄 책의 id
	 * @param model    정보를 넘길 model
	 * @return 수정화면
	 */
	@GetMapping("/publisher/book/update/{bookId}")
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

	/**
	 *  책 수정 처리 post
	 * @param bookId    수정할 책의 id
	 * @param bookRequest 수정할 책의 내용
	 * @return 책 수정 이후 책 리스트로 이동
	 */
	@PostMapping(value = "/publisher/book/update/{bookId}", consumes = "multipart/form-data")
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
	 * 관리자 도서 관리 화면
	 * @return 관리자 도서 관리 화면
	 */
	@GetMapping("/admin/book/management")
	public String management() {
		return "admin/admin_book";
	}

	/**
	 * 도서 삭제
	 * @param bookId 도서 삭제할 아이디
	 * @return 도서 삭제 후 관리자로 다시감
	 */
	@GetMapping("/admin/book/delete/{bookId}")
	public String deleteBook(@PathVariable Long bookId) {
		bookService.deleteBook(bookId);
		return "redirect:/admin/book/management";
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
			if (!Objects.nonNull(categoryResponse.getChildrenList()) || categoryResponse.getChildrenList()
				.isEmpty()) {
				continue;
			}
			categoryResponseList.addAll(parentCategoryGetAllCategoryResponse(categoryResponse.getChildrenList()));

		}
		return categoryResponseList;
	}

}
