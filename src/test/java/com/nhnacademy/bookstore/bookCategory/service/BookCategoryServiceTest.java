package com.nhnacademy.bookstore.bookCategory.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.nhnacademy.bookstore.book.book.dto.response.BookListResponse;
import com.nhnacademy.bookstore.book.book.exception.BookDoesNotExistException;
import com.nhnacademy.bookstore.book.book.repository.BookRepository;
import com.nhnacademy.bookstore.book.bookCartegory.dto.request.CreateBookCategoryRequest;
import com.nhnacademy.bookstore.book.bookCartegory.dto.request.UpdateBookCategoryRequest;
import com.nhnacademy.bookstore.book.bookCartegory.dto.response.BookCategoriesChildrenResponse;
import com.nhnacademy.bookstore.book.bookCartegory.dto.response.BookCategoriesResponse;
import com.nhnacademy.bookstore.book.bookCartegory.repository.BookCategoryRepository;
import com.nhnacademy.bookstore.book.bookCartegory.service.impl.BookCategoryServiceImpl;
import com.nhnacademy.bookstore.book.category.exception.CategoryNotFoundException;
import com.nhnacademy.bookstore.book.category.repository.CategoryRepository;
import com.nhnacademy.bookstore.entity.book.Book;
import com.nhnacademy.bookstore.entity.bookCategory.BookCategory;
import com.nhnacademy.bookstore.entity.category.Category;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(MockitoExtension.class)
class BookCategoryServiceTest {

	@Mock
	private BookRepository bookRepository;

	@Mock
	private CategoryRepository categoryRepository;

	@Mock
	private BookCategoryRepository bookCategoryRepository;

	@InjectMocks
	private BookCategoryServiceImpl bookCategoryService;

	private Book book;
	private Category category;
	private List<Category> categoryList;

	@BeforeEach
	void setUp() {
		category = new Category();
		category.setName("테스트 카테고리");

		categoryList = List.of(category);

		book = new Book(
			"Test Title",
			"Test Description",
			ZonedDateTime.now(),
			1000,
			10,
			900,
			0,
			true,
			"Test Author",
			"123456789",
			"Test Publisher",
			null,
			null,
			null
		);

		book.getBookCategoryList().clear();
	}

	@DisplayName("도서-카테고리 생성 테스트")
	@Test
	void createBookCategory() {
		CreateBookCategoryRequest dto = new CreateBookCategoryRequest(book.getId(),
			categoryList.stream().map(Category::getId).collect(Collectors.toList()));

		when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
		when(categoryRepository.findAllById(anyList())).thenReturn(categoryList);
		when(bookCategoryRepository.existsByBookAndCategory(any(), any())).thenReturn(false);

		bookCategoryService.createBookCategory(dto);

		verify(bookRepository, times(1)).save(book);
		assertTrue(book.getBookCategoryList().stream()
			.anyMatch(bc -> bc.getCategory().equals(category)));
	}

	@DisplayName("도서-카테고리 생성 예외 테스트 - 존재하지 않는 도서")
	@Test
	void createBookCategory_BookDoesNotExist() {
		CreateBookCategoryRequest dto = new CreateBookCategoryRequest(book.getId(),
			categoryList.stream().map(Category::getId).collect(Collectors.toList()));

		when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

		assertThrows(BookDoesNotExistException.class,
			() -> bookCategoryService.createBookCategory(dto));
	}

	@DisplayName("도서-카테고리 생성 예외 테스트 - 존재하지 않는 카테고리")
	@Test
	void createBookCategory_CategoryNotFound() {
		CreateBookCategoryRequest dto = new CreateBookCategoryRequest(book.getId(),
			categoryList.stream().map(Category::getId).collect(Collectors.toList()));

		when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
		when(categoryRepository.findAllById(anyList())).thenReturn(Collections.emptyList());

		assertThrows(CategoryNotFoundException.class,
			() -> bookCategoryService.createBookCategory(dto));
	}

	@DisplayName("도서-카테고리 수정 테스트")
	@Test
	void updateBookCategory() {
		UpdateBookCategoryRequest dto = new UpdateBookCategoryRequest(book.getId(),
			categoryList.stream().map(Category::getId).collect(Collectors.toList()));

		when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
		when(categoryRepository.findAllById(anyList())).thenReturn(categoryList);

		bookCategoryService.updateBookCategory(book.getId(), dto);

		verify(bookRepository, times(1)).findById(anyLong());
		verify(categoryRepository, times(1)).findAllById(anyList());
		verify(bookRepository, times(1)).save(any(Book.class));

		log.info(book.getBookCategoryList().toString());
		assertEquals(1, book.getBookCategoryList().size());
		assertEquals(category, book.getBookCategoryList().get(0).getCategory());
	}

	@DisplayName("도서-카테고리 삭제 테스트")
	@Test
	void deletedBookCategory() {
		BookCategory bookCategory = new BookCategory();
		bookCategory.setBook(book);
		bookCategory.setCategory(category);
		book.addBookCategory(bookCategory);

		when(bookCategoryRepository.findById(anyLong())).thenReturn(Optional.of(bookCategory));

		bookCategoryService.deletedBookCategory(1L);

		verify(bookCategoryRepository, times(1)).deleteById(anyLong());
	}

	@DisplayName("도서에 해당하는 카테고리 목록 조회 테스트")
	@Test
	void readBookWithCategoryList() {
		List<BookCategoriesResponse> categoriesResponseList = new ArrayList<>();
		categoriesResponseList.add(BookCategoriesResponse.builder().id(1L).categoryName("국내").parentId(null).build());
		categoriesResponseList.add(BookCategoriesResponse.builder().id(2L).categoryName("해외").parentId(null).build());
		categoriesResponseList.add(BookCategoriesResponse.builder().id(3L).categoryName("소설").parentId(2L).build());
		categoriesResponseList.add(BookCategoriesResponse.builder().id(4L).categoryName("SF").parentId(3L).build());
		categoriesResponseList.add(BookCategoriesResponse.builder().id(5L).categoryName("추리").parentId(4L).build());
		categoriesResponseList.add(BookCategoriesResponse.builder().id(6L).categoryName("다이어트").parentId(10L).build());
		categoriesResponseList.add(BookCategoriesResponse.builder().id(7L).categoryName("정보").parentId(1L).build());
		categoriesResponseList.add(BookCategoriesResponse.builder().id(8L).categoryName("주식").parentId(7L).build());
		categoriesResponseList.add(BookCategoriesResponse.builder().id(9L).categoryName("여행").parentId(10L).build());
		categoriesResponseList.add(BookCategoriesResponse.builder().id(10L).categoryName("건강/취미").parentId(1L).build());

		BookCategoriesChildrenResponse childrenResponse1 = BookCategoriesChildrenResponse.builder()
			.id(8L)
			.name("주식")
			.children(null)
			.build();
		BookCategoriesChildrenResponse childrenResponse2 = BookCategoriesChildrenResponse.builder()
			.id(6L)
			.name("다이어트")
			.children(null)
			.build();
		BookCategoriesChildrenResponse childrenResponse3 = BookCategoriesChildrenResponse.builder()
			.id(9L)
			.name("여행")
			.children(null)
			.build();
		BookCategoriesChildrenResponse childrenResponse4 = BookCategoriesChildrenResponse.builder()
			.id(5L)
			.name("추리")
			.children(null)
			.build();

		BookCategoriesChildrenResponse childrenResponse5 = BookCategoriesChildrenResponse.builder()
			.id(7L)
			.name("정보")
			.children(List.of(childrenResponse1))
			.build();
		BookCategoriesChildrenResponse childrenResponse6 = BookCategoriesChildrenResponse.builder()
			.id(10L)
			.name("건강/취미")
			.children(List.of(childrenResponse2, childrenResponse3))
			.build();
		BookCategoriesChildrenResponse childrenResponse7 = BookCategoriesChildrenResponse.builder()
			.id(4L)
			.name("SF")
			.children(List.of(childrenResponse4))
			.build();

		BookCategoriesChildrenResponse childrenResponse8 = BookCategoriesChildrenResponse.builder()
			.id(3L)
			.name("소설")
			.children(List.of(childrenResponse7))
			.build();

		BookCategoriesChildrenResponse childrenResponse9 = BookCategoriesChildrenResponse.builder()
			.id(1L)
			.name("국내")
			.children(List.of(childrenResponse5, childrenResponse6))
			.build();
		BookCategoriesChildrenResponse childrenResponse10 = BookCategoriesChildrenResponse.builder()
			.id(2L)
			.name("해외")
			.children(List.of(childrenResponse8))
			.build();

		List<BookCategoriesChildrenResponse> bookCategoriesChildrenResponseList = List.of(childrenResponse9,
			childrenResponse10);

		when(bookCategoryRepository.bookWithCategoryList(anyLong())).thenReturn(categoriesResponseList);

		List<BookCategoriesChildrenResponse> getResponse = bookCategoryService.readBookWithCategoryList(1L);

		assertEquals(getResponse.size(), bookCategoriesChildrenResponseList.size());
		assertEquals(getResponse.getFirst().id(), bookCategoriesChildrenResponseList.getFirst().id());
		assertEquals(getResponse.getFirst().name(), bookCategoriesChildrenResponseList.getFirst().name());

	}

	@DisplayName("카테고리에 해당하는 도서 목록 조회 테스트")
	@Test
	void readCategoriesWithBookList() {
		Pageable pageable = PageRequest.of(0, 10);
		List<BookListResponse> bookList = List.of(
			new BookListResponse(book.getId(), book.getTitle(), book.getPrice(), book.getSellingPrice(),
				book.getAuthor(), null));
		Page<BookListResponse> expectedPage = new PageImpl<>(bookList, pageable, bookList.size());

		when(categoryRepository.findAllById(anyList())).thenReturn(categoryList);
		when(bookCategoryRepository.categoriesWithBookList(anyList(),
			any(Pageable.class))).thenReturn(
			expectedPage);

		Page<BookListResponse> actualPage = bookCategoryService.readCategoriesWithBookList(
			categoryList.stream().map(Category::getId).collect(Collectors.toList()), pageable);

		assertEquals(expectedPage, actualPage);
	}
}