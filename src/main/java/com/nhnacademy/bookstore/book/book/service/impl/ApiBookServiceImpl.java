package com.nhnacademy.bookstore.book.book.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nhnacademy.bookstore.book.book.dto.response.ApiCreateBookResponse;
import com.nhnacademy.bookstore.book.book.repository.ApiBookRepository;
import com.nhnacademy.bookstore.book.book.repository.BookRepository;
import com.nhnacademy.bookstore.book.book.service.ApiBookService;
import com.nhnacademy.bookstore.book.bookCartegory.repository.BookCategoryRepository;
import com.nhnacademy.bookstore.book.category.exception.CategoryNotFoundException;
import com.nhnacademy.bookstore.book.category.repository.CategoryRepository;
import com.nhnacademy.bookstore.entity.book.Book;
import com.nhnacademy.bookstore.entity.bookCategory.BookCategory;
import com.nhnacademy.bookstore.entity.category.Category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 한민기
 *
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ApiBookServiceImpl implements ApiBookService {
	private final ApiBookRepository apiBookRepository;
	private final BookRepository bookRepository;
	private final CategoryRepository categoryRepository;
	private final BookCategoryRepository bookCategoryRepository;

	@Transactional
	@Override
	public void save(String isbnId) {
		ApiCreateBookResponse bookResponse = apiBookRepository.getBookResponse(isbnId);
		log.info("bookResponse : {}", bookResponse);
		Book book = new Book(
			bookResponse.title(),
			bookResponse.item().getFirst().description(),
			stringToZonedDateTime(bookResponse.pubDate()),
			bookResponse.item().getFirst().priceSales(),
			100,
			bookResponse.item().getFirst().priceSales(),
			0,
			true,
			bookResponse.item().getFirst().author(),
			bookResponse.item().getFirst().isbn13(),
			bookResponse.item().getFirst().publisher(),
			null,
			null,
			null
		);
		bookRepository.save(book);

		List<String> categories = categoryNameStringToList(bookResponse.item().getFirst().categoryName());

		log.info("categories : {}", categories);

		for (String categoryName : categories) {
			Optional<Category> category = categoryRepository.findByName(categoryName);

			if (category.isEmpty()) {
				throw new CategoryNotFoundException(categoryName);
			}
			BookCategory bookCategory = BookCategory.create(book, category.get());

			bookCategoryRepository.save(bookCategory);
		}

	}

	/**
	 * String -> ZoneDateTime 으로 변경
	 * @param dateStr 바꿀 date String  형태는 yyyy-MM-dd
	 * @return ZoneDateTime 의 날짜
	 */
	private ZonedDateTime stringToZonedDateTime(String dateStr) {
		if (Objects.isNull(dateStr)) {
			return null;
		}
		DateTimeFormatter formatter = DateTimeFormatter.RFC_1123_DATE_TIME;
		LocalDate localDateStr = LocalDate.parse(dateStr, formatter);

		return localDateStr.atStartOfDay(ZoneId.systemDefault());
	}

	/**
	 * 하나의 카테고리를 나눠서 넣기
	 * ex) 국내도서>사회과학>비평/칼럼>정치비평/칼럼
	 * @param categoryName 하나로 길게 되어 있는 카테고리 이름
	 * @return List 로 나눠진 카테고리
	 */
	private List<String> categoryNameStringToList(String categoryName) {
		String[] categoryNameArray = categoryName.split(">");
		return new ArrayList<>(Arrays.asList(categoryNameArray));
	}

}
