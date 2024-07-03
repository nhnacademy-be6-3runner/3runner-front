package com.nhnacademy.bookstore.book.book.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nhnacademy.bookstore.book.book.dto.request.CreateBookRequest;
import com.nhnacademy.bookstore.book.book.dto.response.BookListResponse;
import com.nhnacademy.bookstore.book.book.dto.response.ReadBookResponse;
import com.nhnacademy.bookstore.book.book.exception.BookDoesNotExistException;
import com.nhnacademy.bookstore.book.book.repository.BookRepository;
import com.nhnacademy.bookstore.book.book.service.BookService;
import com.nhnacademy.bookstore.book.bookCartegory.dto.request.CreateBookCategoryRequest;
import com.nhnacademy.bookstore.book.bookCartegory.dto.request.UpdateBookCategoryRequest;
import com.nhnacademy.bookstore.book.bookCartegory.service.BookCategoryService;
import com.nhnacademy.bookstore.book.bookImage.service.BookImageService;
import com.nhnacademy.bookstore.book.bookTag.dto.request.CreateBookTagListRequest;
import com.nhnacademy.bookstore.book.bookTag.service.BookTagService;
import com.nhnacademy.bookstore.entity.book.Book;
import com.nhnacademy.bookstore.entity.bookImage.enums.BookImageType;

import lombok.RequiredArgsConstructor;

/**
 * 책 서비스 구현체.
 *
 * @author 김병우
 */
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
	private final BookRepository bookRepository;
	private final BookCategoryService bookCategoryService;
	private final BookTagService bookTagService;
	private final BookImageService bookImageService;

	/**
	 * 책 등록 기능.
	 *
	 * @param createBookRequest createBookRequest form
	 */
	// Dto -> save book
	@Override
	@Transactional
	public void createBook(CreateBookRequest createBookRequest) {
		Book book = new Book(
			createBookRequest.title(),
			createBookRequest.description(),
			createBookRequest.publishedDate(),
			createBookRequest.price(),
			createBookRequest.quantity(),
			createBookRequest.sellingPrice(),
			0,
			createBookRequest.packing(),
			createBookRequest.author(),
			createBookRequest.isbn(),
			createBookRequest.publisher(),
			null,
			null,
			null
		);
		bookRepository.save(book);

		bookCategoryService.createBookCategory(
			CreateBookCategoryRequest.builder()
				.bookId(book.getId())
				.categoryIds(createBookRequest.categoryIds())
				.build());
		bookTagService.createBookTag(
			CreateBookTagListRequest.builder().bookId(book.getId()).tagIdList(createBookRequest.tagIds()).build());
		bookImageService.createBookImage(createBookRequest.imageList(), book.getId(), BookImageType.DESCRIPTION);
		if (!Objects.isNull(createBookRequest.imageName())) {
			bookImageService.createBookImage(List.of(createBookRequest.imageName()), book.getId(), BookImageType.MAIN);
		}
	}

	/**
	 * 책 조회 기능.
	 *
	 * @param bookId book entity id
	 */
	@Override
	public ReadBookResponse readBookById(Long bookId) {
		ReadBookResponse book = bookRepository.readDetailBook(bookId);
		bookRepository.viewBook(bookId);
		if (Objects.isNull(book)) {
			throw new BookDoesNotExistException("요청하신 책이 존재하지 않습니다.");
		}
		return book;
	}

	/**
	 * 책의 정보를 업데이트하는 항목입니다.
	 * @param bookId 책의 아이디
	 * @param createBookRequest 책의 수정 정보
	 */
	@Override
	@Transactional
	public void updateBook(Long bookId, CreateBookRequest createBookRequest) {
		Book book = bookRepository.findById(bookId)
			.orElseThrow(() -> new BookDoesNotExistException("요청하신 책이 존재하지 않습니다."));

		book.setTitle(createBookRequest.title());
		book.setDescription(createBookRequest.description());
		book.setPublishedDate(createBookRequest.publishedDate());
		book.setPrice(createBookRequest.price());
		book.setQuantity(createBookRequest.quantity());
		book.setSellingPrice(createBookRequest.sellingPrice());
		book.setPacking(createBookRequest.packing());
		book.setAuthor(createBookRequest.author());
		book.setIsbn(createBookRequest.isbn());
		book.setPublisher(createBookRequest.publisher());

		bookRepository.save(book);

		bookCategoryService.updateBookCategory(bookId,
			UpdateBookCategoryRequest.builder().bookId(bookId).categoryIds(createBookRequest.categoryIds()).build());
		bookTagService.updateBookTag(
			CreateBookTagListRequest.builder().bookId(bookId).tagIdList(createBookRequest.tagIds()).build());

		bookImageService.updateBookImage(createBookRequest.imageName(), createBookRequest.imageList(), bookId);
	}

	@Override
	public Page<BookListResponse> readAllBooks(Pageable pageable) {
		return bookRepository.readBookList(pageable);
	}

	@Override
	public void deleteBook(Long bookId) {
		bookRepository.deleteById(bookId);
	}
}
