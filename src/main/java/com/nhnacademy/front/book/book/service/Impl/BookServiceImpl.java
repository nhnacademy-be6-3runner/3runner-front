package com.nhnacademy.front.book.book.service.Impl;

import com.nhnacademy.front.book.book.controller.feign.ApiBookClient;
import com.nhnacademy.front.book.book.controller.feign.BookClient;
import com.nhnacademy.front.book.book.dto.request.CreateBookRequest;
import com.nhnacademy.front.book.book.dto.request.UserCreateBookRequest;
import com.nhnacademy.front.book.book.dto.response.BookDocumentResponse;
import com.nhnacademy.front.book.book.dto.response.BookListResponse;
import com.nhnacademy.front.book.book.dto.response.BookManagementResponse;
import com.nhnacademy.front.book.book.dto.response.UserReadBookResponse;
import com.nhnacademy.front.book.book.exception.InvalidApiResponseException;
import com.nhnacademy.front.book.book.exception.NotFindBookException;
import com.nhnacademy.front.book.book.service.BookService;
import com.nhnacademy.front.book.categroy.controller.feign.CategoryClient;
import com.nhnacademy.front.util.ApiResponse;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * 책 관련 내용 서비스.
 *
 * @author 한민기
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

	private final BookClient bookClient;
	private final ApiBookClient apiBookClient;
	private final CategoryClient categoryClient;

	/**
	 * 입력 받은 항목을 Api 서버에게 보내주기 위한 형태로 변형.
	 *
	 * @param userCreateBookRequest 입력 받은 항목들
	 */
	@Override
	@CacheEvict(value = {"BookPage", "CategoryBooks", "AdminBookPage"}, allEntries = true)
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

	/**
	 * isbn 으로 책 만들기 메소드.
	 *
	 * @param isbn 책의 isbn
	 */
	@Override
	@CacheEvict(value = {"BookPage", "CategoryBooks", "AdminBookPage"}, allEntries = true)
	public void saveApiBook(String isbn) {
		apiBookClient.createApiBook(isbn);
	}

	/**
	 * 책 상세보기 서비스 메소드.
	 *
	 * @param bookId 책의 아이디
	 * @return 책의 정보
	 */
	@Override
	public UserReadBookResponse readBook(long bookId) {
		ApiResponse<UserReadBookResponse> getResponse = bookClient.getDetailBookById(bookId);
		if (!getResponse.getHeader().isSuccessful()) {
			throw new NotFindBookException();
		}
		return getResponse.getBody().getData();
	}

	/**
	 * 책 업데이트 서비스 메소드.
	 *
	 * @param bookId    책 아이디
	 * @param userCreateBookRequest    책의 수정 내용
	 * @param imageName    책의 표지 이미지
	 */
	@Override
	@CacheEvict(value = {"BookPage", "CategoryBooks", "AdminBookPage"}, allEntries = true)
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

	/**
	 * 책 삭제 서비스 메소드.
	 *
	 * @param bookId 삭제할 책의 id
	 */
	@Override
	@CacheEvict(value = {"BookPage", "CategoryBooks", "AdminBookPage"}, allEntries = true)
	public void deleteBook(long bookId) {
		bookClient.deleteBook(bookId);
	}

	/**
	 * 키워드를 통한 책 검색.
	 *
	 * @param keyword 책의 키워드
	 * @param page 책의 페이지
	 * @param size 책 사이즈
	 * @return 정보 리턴
	 */
	@Override
	@Cacheable(value = "Search", key = "#page + '-' + #size + '-' +#keyword", cacheManager = "cacheManager")
	public Page<BookDocumentResponse> searchReadAllBooks(String keyword, int page, int size) {
		ApiResponse<Page<BookDocumentResponse>> response = bookClient.searchReadAllBooks(page, size, keyword);
		if (!response.getHeader().isSuccessful()) {
			throw new NotFindBookException();
		}
		return response.getBody().getData();
	}

	/**
	 * 카테고리에 포함 된 도서 리스트 보기.
	 *
	 * @param page 페이지
	 * @param size 사이즈
	 * @param sort 정렬
	 * @param categoryId 카테고리 넘버
	 * @return 정보 리턴
	 */
	@Override
	@Cacheable(value = "CategoryBooks", key = "#page + '-' + #size + '-' + #sort + '-' + #categoryId", cacheManager = "cacheManager")
	public Page<BookListResponse> readCategoryAllBooks(int page, int size, String sort, long categoryId) {
		ApiResponse<Page<BookListResponse>> response = categoryClient.readCategoryAllBooks(page, size, sort,
			categoryId);

		if (response.getHeader().isSuccessful() && response.getBody() != null) {
			return response.getBody().getData();
		} else {
			throw new InvalidApiResponseException("도서 페이지 조회 exception");
		}
	}

	/**
	 * 내용 에서 이미지 추출하는 코드.
	 *
	 * @param description 내용
	 * @return 추출한 이미지 리스트
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
	 * String 으로 되어있는 아이디 값들을 리스트로 변환.
	 *
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
	 * String -> ZoneDateTime 으로 변경.
	 *
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
	 * 도서 페이지 조회 메서드입니다.
	 *
	 * @param page 페이지
	 * @param size 사이즈
	 * @return 도서 리스트
	 */
	@Override
	@Cacheable(value = "BookPage", key = "#page + '-' + #size + '-' + #sort", cacheManager = "cacheManager")
	public Page<BookListResponse> readAllBooks(int page, int size, String sort) {
		ApiResponse<Page<BookListResponse>> response = bookClient.readAllBooks(page, size, sort);

		if (response.getHeader().isSuccessful() && response.getBody() != null) {
			return response.getBody().getData();
		} else {
			throw new InvalidApiResponseException("도서 페이지 조회 exception");
		}
	}

	/**
	 * 관리자페이지에서 책 가져오기.
	 *
	 * @param page 페이지
	 * @param size 사이즈
	 * @return 책 리스트
	 */
	@Override
	@Cacheable(value = "AdminBookPage", key = "#page + '-' + #size", cacheManager = "cacheManager")
	public Page<BookManagementResponse> readAllAdminBooks(int page, int size) {
		ApiResponse<Page<BookManagementResponse>> response = bookClient.readAllAdminBooks(page, size);

		if (response.getHeader().isSuccessful() && response.getBody() != null) {
			return response.getBody().getData();
		} else {
			throw new InvalidApiResponseException("도서 페이지 조회 exception");
		}
	}
}