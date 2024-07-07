package com.nhnacademy.bookstore.book.book.service;

import com.nhnacademy.bookstore.book.book.dto.request.CreateBookRequest;
import com.nhnacademy.bookstore.book.book.dto.response.BookForCouponResponse;
import com.nhnacademy.bookstore.book.book.dto.response.BookListResponse;
import com.nhnacademy.bookstore.book.book.dto.response.ReadBookResponse;
import com.nhnacademy.bookstore.book.book.exception.BookDoesNotExistException;
import com.nhnacademy.bookstore.book.book.repository.BookRepository;
import com.nhnacademy.bookstore.book.book.service.impl.BookServiceImpl;
import com.nhnacademy.bookstore.book.bookCartegory.dto.request.CreateBookCategoryRequest;
import com.nhnacademy.bookstore.book.bookCartegory.service.BookCategoryService;
import com.nhnacademy.bookstore.book.bookImage.service.BookImageService;
import com.nhnacademy.bookstore.book.bookTag.dto.request.CreateBookTagListRequest;
import com.nhnacademy.bookstore.book.bookTag.service.BookTagService;
import com.nhnacademy.bookstore.entity.book.Book;
import com.nhnacademy.bookstore.entity.bookImage.enums.BookImageType;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookCategoryService bookCategoryService;
    @Mock
    private BookTagService bookTagService;
    @Mock
    private BookImageService bookImageService;

    @Test
    void testCreateBook() {
        CreateBookRequest request = new CreateBookRequest(
                "Test Title",
                "Test Description",
                ZonedDateTime.now(),
                1000,
                10,
                900,
                true,
                "Test Author",
                "123456789",
                "Test Publisher",
                "asdf.jpg",
                List.of("a.jpg", "b.jpg"),
                List.of(1L, 2L, 3L),
                List.of(1L, 2L, 3L)
        );

        bookService.createBook(request);

        assertThat(request.imageList().size()).hasSameClassAs(2);
        assertThat(request.tagIds().size()).hasSameClassAs(3);
        assertThat(request.categoryIds().size()).hasSameClassAs(3);
        verify(bookRepository, times(1)).save(any(Book.class));
        verify(bookCategoryService, times(1)).createBookCategory(any(CreateBookCategoryRequest.class));
        verify(bookTagService, times(1)).createBookTag(any(CreateBookTagListRequest.class));
        verify(bookImageService, times(2)).createBookImage(anyList(), anyLong(), any(BookImageType.class));
    }

    @Test
    void testReadBookById_Success() {
        ReadBookResponse readBookResponse = ReadBookResponse.builder()
                .id(1L)
                .title("test Title")
                .description("Test description")
                .publishedDate(ZonedDateTime.now())
                .price(10000)
                .quantity(10)
                .sellingPrice(10000)
                .viewCount(777)
                .packing(true)
                .author("Test Author")
                .isbn("1234567890123")
                .publisher("Test Publisher")
                .imagePath("Test Image Path")

                .build();

        when(bookRepository.readDetailBook(anyLong())).thenReturn(readBookResponse);

        ReadBookResponse foundBook = bookService.readBookById(1L);

        assertEquals(readBookResponse.id(), foundBook.id());
    }

    @Test
    void testReadBookById_NotFound() {
        when(bookRepository.readDetailBook(anyLong())).thenReturn(null);

        assertThrows(BookDoesNotExistException.class, () -> bookService.readBookById(1L));

    }

    @Test
    void testReadAllBooks_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        BookListResponse bookResponse =
                BookListResponse.builder()
                        .id(1L)
                        .title("test Title")
                        .price(12344)
                        .sellingPrice(1234444)
                        .author("Test Author")
                        .thumbnail("Test Thumbnail")
                        .build();
        Page<BookListResponse> bookPage = new PageImpl<>(Collections.singletonList(bookResponse), pageable, 1);

        when(bookRepository.readBookList(any(Pageable.class))).thenReturn(bookPage);

        assertEquals(bookPage.getTotalElements(), bookService.readAllBooks(pageable).getTotalElements());
    }

    @Test
    void readBookByIds() {
        Book book = new Book("Sample Book", "Sample Description", ZonedDateTime.now(),
                100, 50, 80, 500, true, "John Doe",
                "1234567789", "Sample Publisher", null, null, null);
        book.setId(1L);

        List<Book> bookList = List.of(book);

        when(bookRepository.findAllById(List.of(1L))).thenReturn(bookList);

        List<BookForCouponResponse> bookResponseList = bookService.readBookByIds(List.of(1L));
        assertEquals(1, bookResponseList.size());
        assertEquals(book.getTitle(), bookResponseList.getFirst().title());
        assertEquals(book.getId(), bookResponseList.getFirst().id());

    }
}