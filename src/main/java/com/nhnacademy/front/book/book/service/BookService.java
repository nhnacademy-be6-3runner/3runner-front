package com.nhnacademy.front.book.book.service;

import org.springframework.data.domain.Page;

import com.nhnacademy.front.book.book.dto.request.UserCreateBookRequest;
import com.nhnacademy.front.book.book.dto.response.BookDocumentResponse;
import com.nhnacademy.front.book.book.dto.response.BookListResponse;
import com.nhnacademy.front.book.book.dto.response.BookManagementResponse;
import com.nhnacademy.front.book.book.dto.response.UserReadBookResponse;

public interface BookService {
	void saveBook(UserCreateBookRequest createBookRequest, String imageName);

	/**
	 * 도서 페이지 조회 메서드입니다.
	 * @param page 페이지
	 * @param size 사이즈
	 * @return 도서 리스트
	 */
	Page<BookListResponse> readAllBooks(int page, int size, String sort);

	/**
	 * 관리자 페이지에서 볼 도서 목록
	 * @param page 페이지
	 * @param size 사이즈
	 * @return 도서 리스트
	 */
	Page<BookManagementResponse> readAllAdminBooks(int page, int size);

	void saveApiBook(String isbn);

	UserReadBookResponse readBook(long bookId);

	void updateBook(long bookId, UserCreateBookRequest createBookRequest, String imageName);

	/**
	 * 책 삭제하는 서비스
	 * @param bookId 삭제할 책의 id
	 */
	void deleteBook(long bookId);

	Page<BookDocumentResponse> searchReadAllBooks(String keyword, int page, int size);

	Page<BookListResponse> readCategoryAllBooks(int page, int size, String sort, long categoryId);
}