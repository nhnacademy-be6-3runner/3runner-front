package com.nhnacademy.bookstore.book.bookImage.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nhnacademy.bookstore.book.book.repository.BookRepository;
import com.nhnacademy.bookstore.book.bookImage.dto.request.CreateBookImageRequest;
import com.nhnacademy.bookstore.book.bookImage.repository.BookImageRepository;
import com.nhnacademy.bookstore.book.bookImage.service.BookImageService;
import com.nhnacademy.bookstore.book.image.exception.NotFindImageException;
import com.nhnacademy.bookstore.entity.book.Book;
import com.nhnacademy.bookstore.entity.bookImage.BookImage;
import com.nhnacademy.bookstore.entity.bookImage.enums.BookImageType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookImageServiceImpl implements BookImageService {

	private final BookImageRepository bookImageRepository;
	private final BookRepository bookRepository;

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public void createBookImage(List<String> imageList, long bookId, BookImageType bookImageType) {
		List<CreateBookImageRequest> createBookImageRequestList = new ArrayList<>();
		for (String image : imageList) {
			createBookImageRequestList.add(new CreateBookImageRequest(image, bookImageType, bookId));
		}
		createBookImage(createBookImageRequestList);
	}

	/**
	 * List 로 받아와서 한꺼번에 추가
	 * book Id 가 같기 떄문에 한번만 받아와 다 추가
	 * @param bookImageRequestList 추가할 book 과 Image
	 */
	@Override
	public void createBookImage(List<CreateBookImageRequest> bookImageRequestList) {
		if (Objects.isNull(bookImageRequestList) || bookImageRequestList.isEmpty()) {
			return;
		}

		Optional<Book> book = bookRepository.findById(bookImageRequestList.getFirst().bookId());
		if (book.isEmpty()) {
			throw new NotFindImageException();
		}
		for (CreateBookImageRequest bookImageRequest : bookImageRequestList) {

			BookImage bookImageEntity = new BookImage(bookImageRequest.bookId(),
				bookImageRequest.url(),
				bookImageRequest.type(),
				book.get());

			bookImageRepository.save(bookImageEntity);
		}
	}

	/**
	 * Book Image 다대다 연결을 위한 함수
	 * @param bookImageRequest  bookImageRequestDto
	 */
	@Override
	public void createBookImage(CreateBookImageRequest bookImageRequest) {
		Optional<Book> book = bookRepository.findById(bookImageRequest.bookId());
		if (book.isEmpty()) {
			throw new NotFindImageException();
		}
		BookImage bookImageEntity = new BookImage(bookImageRequest.bookId(),
			bookImageRequest.url(),
			bookImageRequest.type(),
			book.get());
		bookImageRepository.save(bookImageEntity);
	}
}
