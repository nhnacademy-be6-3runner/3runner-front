package com.nhnacademy.bookstore.bookCategory.repository;

import com.nhnacademy.bookstore.book.book.dto.response.BookListResponse;
import com.nhnacademy.bookstore.book.book.repository.BookRepository;
import com.nhnacademy.bookstore.book.bookCartegory.dto.response.BookCategoriesResponse;
import com.nhnacademy.bookstore.book.bookCartegory.repository.BookCategoryRepository;
import com.nhnacademy.bookstore.book.bookCartegory.repository.impl.BookCategoryCustomRepositoryImpl;
import com.nhnacademy.bookstore.book.category.repository.CategoryRepository;
import com.nhnacademy.bookstore.entity.book.Book;
import com.nhnacademy.bookstore.entity.bookCategory.BookCategory;
import com.nhnacademy.bookstore.entity.category.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.ZonedDateTime;
import java.util.List;

@DataJpaTest
@Import(BookCategoryCustomRepositoryImpl.class)
public class BookCategoryRepositoryTest {

    @Autowired
    private BookCategoryRepository bookCategoryRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    private Book book;
    private Category category;
    private List<Category> categoryList;

    @BeforeEach
    public void setup() {
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
        Category parent1 = Category.builder()
                .name("부모 카데고리1")
                .parent(null)
                .build();
        Category children1 = Category.builder()
                .name("자식 카데고리1")
                .parent(parent1)
                .build();
        Category children2 = Category.builder()
                .name("자식 카데고리2")
                .parent(parent1)
                .build();
        this.categoryList = List.of(parent1, children1, children2);
    }

    @DisplayName("도서에 해당하는 카테고리 모두 삭제 테스트")
    @Test
    void deletedByBookTest() {
        categoryRepository.saveAll(categoryList);
        bookRepository.save(book);

        BookCategory bookCategory1 = BookCategory.builder()
                .book(book)
                .category(categoryList.get(0))
                .build();
        BookCategory bookCategory2 = BookCategory.builder()
                .book(book)
                .category(categoryList.get(1))
                .build();
        BookCategory bookCategory3 = BookCategory.builder()
                .book(book)
                .category(categoryList.get(2))
                .build();
        bookCategoryRepository.saveAll(List.of(bookCategory1, bookCategory2, bookCategory3));

        bookCategoryRepository.deleteByBook(book);

        List<BookCategory> remainingBookCategories = bookCategoryRepository.findAll();
        Assertions.assertTrue((remainingBookCategories).isEmpty());
    }

    @DisplayName("책이랑 카테고리가 존재하는지 확인하는 테스트")
    @Test
    void existsByBookAndCategory() {
        Category category = Category.builder()
                .name("카테고리")
                .build();
        Category test = categoryRepository.save(category);
        bookRepository.save(book);
        BookCategory bookCategory1 = BookCategory.builder()
                .book(book)
                .category(test)
                .build();
        bookCategoryRepository.saveAll(List.of(bookCategory1));
        Assertions.assertTrue(bookCategoryRepository.existsByBookAndCategory(book, test));
    }

    @DisplayName("카테고리로 도서 조회 테스트")
    @Test
    void categoryWithBookListTest() {
        categoryRepository.saveAll(categoryList);
        bookRepository.save(book);

        BookCategory bookCategory1 = BookCategory.builder()
                .book(book)
                .category(categoryList.get(0))
                .build();
        bookCategoryRepository.save(bookCategory1);

        Pageable pageable = PageRequest.of(0, 10);
        Page<BookListResponse> bookPage = bookCategoryRepository.categoryWithBookList(
                categoryList.get(0).getId(), pageable);

        Assertions.assertFalse(bookPage.isEmpty());
        Assertions.assertEquals(1, bookPage.getTotalElements());
        Assertions.assertEquals(book.getTitle(), bookPage.getContent().get(0).title());
        Assertions.assertEquals(book.getPrice(), bookPage.getContent().get(0).price());
        Assertions.assertEquals(book.getSellingPrice(), bookPage.getContent().get(0).sellingPrice());
        Assertions.assertEquals(book.getAuthor(), bookPage.getContent().get(0).author());
        // Add assertion for thumbnail if needed
        // Assertions.assertEquals(expectedThumbnail, bookPage.getContent().get(0).thumbnail());
    }

    @DisplayName("도서 아이디로 카테고리 리스트 조회 테스트")
    @Test
    void bookWithCategoryListTest() {
        categoryRepository.saveAll(categoryList);
        bookRepository.save(book);

        BookCategory bookCategory1 = BookCategory.builder()
                .book(book)
                .category(categoryList.get(0))
                .build();
        bookCategoryRepository.save(bookCategory1);

        List<BookCategoriesResponse> categories = bookCategoryRepository.bookWithCategoryList(
                book.getId());

        Assertions.assertFalse(categories.isEmpty());
        Assertions.assertEquals(1, categories.size());
        Assertions.assertEquals(categoryList.get(0).getName(), categories.get(0).categoryName());
    }

    @DisplayName("카테고리 리스트로 도서 조회 테스트")
    @Test
    void categoriesWithBookListTest() {
        categoryRepository.saveAll(categoryList);
        bookRepository.save(book);

        BookCategory bookCategory1 = BookCategory.builder()
                .book(book)
                .category(categoryList.get(0))
                .build();
        bookCategoryRepository.save(bookCategory1);

        Pageable pageable = PageRequest.of(0, 10);
        Page<BookListResponse> bookPage = bookCategoryRepository.categoriesWithBookList(
                List.of(categoryList.get(0).getId()), pageable);

        Assertions.assertFalse(bookPage.isEmpty());
        Assertions.assertEquals(1, bookPage.getTotalElements());
        Assertions.assertEquals(book.getTitle(), bookPage.getContent().get(0).title());
        Assertions.assertEquals(book.getPrice(), bookPage.getContent().get(0).price());
        Assertions.assertEquals(book.getSellingPrice(), bookPage.getContent().get(0).sellingPrice());
        Assertions.assertEquals(book.getAuthor(), bookPage.getContent().get(0).author());
        // Add assertion for thumbnail if needed
//         Assertions.assertEquals(expectedThumbnail, bookPage.getContent().get(0).thumbnail());
    }
}
