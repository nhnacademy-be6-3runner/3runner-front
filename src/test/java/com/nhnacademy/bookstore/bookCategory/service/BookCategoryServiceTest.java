package com.nhnacademy.bookstore.bookCategory.service;

import com.nhnacademy.bookstore.book.book.dto.response.BookListResponse;
import com.nhnacademy.bookstore.book.book.exception.BookDoesNotExistException;
import com.nhnacademy.bookstore.book.book.repository.BookRepository;
import com.nhnacademy.bookstore.book.bookCartegory.dto.request.CreateBookCategoryRequest;
import com.nhnacademy.bookstore.book.bookCartegory.dto.request.UpdateBookCategoryRequest;
import com.nhnacademy.bookstore.book.bookCartegory.dto.response.BookCategoriesResponse;
import com.nhnacademy.bookstore.book.bookCartegory.exception.BookCategoryAlreadyExistsException;
import com.nhnacademy.bookstore.book.bookCartegory.exception.BookCategoryNotFoundException;
import com.nhnacademy.bookstore.book.bookCartegory.repository.BookCategoryRepository;
import com.nhnacademy.bookstore.book.bookCartegory.service.impl.BookCategoryServiceImpl;
import com.nhnacademy.bookstore.book.category.exception.CategoryNotFoundException;
import com.nhnacademy.bookstore.book.category.repository.CategoryRepository;
import com.nhnacademy.bookstore.entity.book.Book;
import com.nhnacademy.bookstore.entity.category.Category;
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

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookCategoryServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private BookCategoryRepository bookCategoryRepository;

    @InjectMocks
    private BookCategoryServiceImpl bookCategoryService; // 구현체를 명시적으로 주입

    private Book book;
    private Category category;
    private List<Category> categoryList;

    @BeforeEach
    void setUp() {
        category = Category.builder()
                .name("테스트 카테고리")
                .build();
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
    }

    @DisplayName("도서-카테고리 생성 테스트")
    @Test
    void createBookCategory() {
        CreateBookCategoryRequest dto = new CreateBookCategoryRequest(book.getId(), categoryList.stream().map(Category::getId).collect(Collectors.toList()));

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(categoryRepository.findAllById(anyList())).thenReturn(categoryList);
        when(bookCategoryRepository.existsByBookAndCategory(any(), any())).thenReturn(false);

        bookCategoryService.createBookCategory(dto);

        verify(bookCategoryRepository, times(1)).saveAll(anyList());
    }

    @DisplayName("도서-카테고리 생성 예외 테스트 - 존재하지 않는 도서")
    @Test
    void createBookCategory_BookDoesNotExist() {
        CreateBookCategoryRequest dto = new CreateBookCategoryRequest(book.getId(), categoryList.stream().map(Category::getId).collect(Collectors.toList()));

        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(BookDoesNotExistException.class, () -> bookCategoryService.createBookCategory(dto));
    }

    @DisplayName("도서-카테고리 생성 예외 테스트 - 존재하지 않는 카테고리")
    @Test
    void createBookCategory_CategoryNotFound() {
        CreateBookCategoryRequest dto = new CreateBookCategoryRequest(book.getId(), categoryList.stream().map(Category::getId).collect(Collectors.toList()));

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(categoryRepository.findAllById(anyList())).thenReturn(Collections.emptyList());

        assertThrows(CategoryNotFoundException.class, () -> bookCategoryService.createBookCategory(dto));
    }

    @DisplayName("도서-카테고리 수정 테스트")
    @Test
    void updateBookCategory() {
        UpdateBookCategoryRequest dto = new UpdateBookCategoryRequest(book.getId(), categoryList.stream().map(Category::getId).collect(Collectors.toList()));

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(categoryRepository.findAllById(anyList())).thenReturn(categoryList);

        bookCategoryService.updateBookCategory(book.getId(), dto);

        verify(bookCategoryRepository, times(1)).deleteByBook(any());
        verify(bookCategoryRepository, times(1)).saveAll(anyList());
    }

    @DisplayName("도서-카테고리 삭제 테스트")
    @Test
    void deletedBookCategory() {
        when(bookCategoryRepository.existsById(anyLong())).thenReturn(true);

        bookCategoryService.deletedBookCategory(1L);

        verify(bookCategoryRepository, times(1)).deleteById(anyLong());
    }

    @DisplayName("도서에 해당하는 카테고리 목록 조회 테스트")
    @Test
    void readBookWithCategoryList() {
        List<BookCategoriesResponse> expectedResponse = List.of(new BookCategoriesResponse(category.getName()));

        when(bookCategoryRepository.bookWithCategoryList(anyLong())).thenReturn(expectedResponse);

        List<BookCategoriesResponse> actualResponse = bookCategoryService.readBookWithCategoryList(book.getId());

        assertEquals(expectedResponse, actualResponse);
    }

    @DisplayName("카테고리에 해당하는 도서 목록 조회 테스트")
    @Test
    void readCategoriesWithBookList() {
        Pageable pageable = PageRequest.of(0, 10);
        List<BookListResponse> bookList = List.of(new BookListResponse(book.getTitle(), book.getPrice(), book.getSellingPrice(), book.getAuthor()));
        Page<BookListResponse> expectedPage = new PageImpl<>(bookList, pageable, bookList.size());

        when(categoryRepository.findAllById(anyList())).thenReturn(categoryList);
        when(bookCategoryRepository.categoriesWithBookList(anyList(), any(Pageable.class))).thenReturn(expectedPage);

        Page<BookListResponse> actualPage = bookCategoryService.readCategoriesWithBookList(categoryList.stream().map(Category::getId).collect(Collectors.toList()), pageable);

        assertEquals(expectedPage, actualPage);
    }
}
