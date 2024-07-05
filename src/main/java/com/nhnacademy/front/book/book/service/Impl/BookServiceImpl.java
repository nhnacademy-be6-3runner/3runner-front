package com.nhnacademy.front.book.book.service.Impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.nhnacademy.front.book.book.controller.feign.ApiBookClient;
import com.nhnacademy.front.book.book.controller.feign.BookClient;
import com.nhnacademy.front.book.book.dto.request.CreateBookRequest;
import com.nhnacademy.front.book.book.dto.request.UserCreateBookRequest;
import com.nhnacademy.front.book.book.dto.response.BookListResponse;
import com.nhnacademy.front.book.book.dto.response.BookManagementResponse;
import com.nhnacademy.front.book.book.dto.response.UserReadBookResponse;
import com.nhnacademy.front.book.book.exception.InvalidApiResponseException;
import com.nhnacademy.front.book.book.exception.NotFindBookException;
import com.nhnacademy.front.book.book.service.BookService;
import com.nhnacademy.front.util.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

	private final BookClient bookClient;
	private final ApiBookClient apiBookClient;

	/**
	 * 입력 받은 항목을 Api 서버에게 보내주기 위한 형태로 변형
	 *  그후 보내기
	 * @param userCreateBookRequest 입력 받은 항목들
	 */
	@Override
	public void saveBook(UserCreateBookRequest userCreateBookRequest, String imageName) {

		CreateBookRequest createBookRequest = CreateBookRequest.builder()
			.title(userCreateBookRequest.title())
			.description(userCreateBookRequest.description())
			.publishedDate(stringToZonedDateTime(userCreateBookRequest.publishedDate()))
			.price(userCreateBookRequest.price())
			.quantity(userCreateBookRequest.quantity())
			.sellingPrice(userCreateBookRequest.sellingPrice())
			.packing(userCreateBookRequest.packing())
			.author(userCreateBookRequest.author())
			.isbn(userCreateBookRequest.isbn())
			.publisher(userCreateBookRequest.publisher())
			.imageName(imageName)
			.imageList(descriptionToImageList(userCreateBookRequest.description()))
			.tagIds(stringIdToList(userCreateBookRequest.tagList()))
			.categoryIds(stringIdToList(userCreateBookRequest.categoryList()))
			.build();

		log.info("Create book : {}", createBookRequest);

		bookClient.createBook(createBookRequest);
	}

	@Override
	public void saveApiBook(String isbn) {
		apiBookClient.createApiBook(isbn);
	}

	@Override
	public UserReadBookResponse readBook(long bookId) {
		ApiResponse<UserReadBookResponse> getResponse = bookClient.getDetailBookById(bookId);
		if (!getResponse.getHeader().isSuccessful()) {
			throw new NotFindBookException();
		}
		return getResponse.getBody().getData();
	}

	@Override
	public void updateBook(long bookId, UserCreateBookRequest userCreateBookRequest, String imageName) {

		CreateBookRequest updateBookRequest = CreateBookRequest.builder()
			.title(userCreateBookRequest.title())
			.description(userCreateBookRequest.description())
			.publishedDate(stringToZonedDateTime(userCreateBookRequest.publishedDate()))
			.price(userCreateBookRequest.price())
			.quantity(userCreateBookRequest.quantity())
			.sellingPrice(userCreateBookRequest.sellingPrice())
			.packing(userCreateBookRequest.packing())
			.author(userCreateBookRequest.author())
			.isbn(userCreateBookRequest.isbn())
			.publisher(userCreateBookRequest.publisher())
			.imageName(imageName)
			.imageList(descriptionToImageList(userCreateBookRequest.description()))
			.tagIds(stringIdToList(userCreateBookRequest.tagList()))
			.categoryIds(stringIdToList(userCreateBookRequest.categoryList()))
			.build();

		bookClient.updateBook(bookId, updateBookRequest);
	}

	@Override
	public void deleteBook(long bookId) {
		bookClient.deleteBook(bookId);
	}

	/**
	 *
	 *  내용 에서 이미지 추출하는 코드
	 * @param description
	 * @return
	 */
	private List<String> descriptionToImageList(String description) {
		List<String> imageList = new ArrayList<>();
		String[] split = description.split("fileName=");
		if (split.length > 1) {
			for (int i = 1; i < split.length; i++) {
				imageList.add(split[i].substring(0, split[i].indexOf('"')));
			}
		}
		log.info("imageList : {}", imageList);
		return imageList;
	}

	/**
	 * String 으로 되어있는 아이디 값들을 리스트로 변환
	 * @param stringId id 가 하나의 String 으로 이어져있음 ex -> 1,2,3,4
	 * @return 리스트로 반환
	 */
	private List<Long> stringIdToList(String stringId) {
		List<Long> idList = new ArrayList<>();
		if (Objects.isNull(stringId)) {
			return idList;
		}
		String[] idSplit = stringId.split(",");

		for (String idStr : idSplit) {
			idList.add(Long.parseLong(idStr));
		}
		log.info("list {}", idList);
		return idList;
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
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDateStr = LocalDate.parse(dateStr, formatter);
		return localDateStr.atStartOfDay(ZoneId.systemDefault());
	}

	/**
	 *
	 * 메인 페이지에 도서 리스트를 불러오는 메서드입니다.
	 * @param limit 제한 갯수
	 * @return 도서 리스트
	 */
	@Override
	public Page<BookListResponse> readLimitBooks(int limit) {
		ApiResponse<Page<BookListResponse>> response = bookClient.readAllBooks(2, limit);

		if (response.getHeader().isSuccessful() && response.getBody() != null) {
			return response.getBody().getData();
		} else {
			throw new InvalidApiResponseException("메인페이지 도서 리스트 조회 exception");
		}
	}

	/**
	 * 도서 페이지 조회 메서드입니다.
	 * @param page 페이지
	 * @param size 사이즈
	 * @return 도서 리스트
	 */
	@Override
	public Page<BookListResponse> readAllBooks(int page, int size) {
		ApiResponse<Page<BookListResponse>> response = bookClient.readAllBooks(page, size);

		if (response.getHeader().isSuccessful() && response.getBody() != null) {
			return response.getBody().getData();
		} else {
			throw new InvalidApiResponseException("도서 페이지 조회 exception");
		}
	}

	@Override
	public Page<BookManagementResponse> readAllAdminBooks(int page, int size) {
		ApiResponse<Page<BookManagementResponse>> response = bookClient.readAllAdminBooks(page, size);

		if (response.getHeader().isSuccessful() && response.getBody() != null) {
			return response.getBody().getData();
		} else {
			throw new InvalidApiResponseException("도서 페이지 조회 exception");
		}
	}
}