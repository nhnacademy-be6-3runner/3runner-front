package com.nhnacademy.front.book.book.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

/**
 * 책읠 생성할 때 사용할 레코드.
 *
 * @author 한민기
 *
 * @param title        제목
 * @param description    설명
 * @param publishedDate 출판일
 * @param price        정가
 * @param quantity        수량
 * @param sellingPrice    판매가
 * @param image            메인 이미지
 * @param packing        포장 가능 여부
 * @param author        작가
 * @param isbn            고유 isbn
 * @param publisher        출판사
 * @param tagList        태그 리스트
 * @param categoryList    카테고리 리스트
 */
public record UserCreateBookRequest(
	@NotBlank(message = "title is entry") String title,
	@NotBlank String description,
	@NotBlank String publishedDate,
	@Min(0) int price,
	@Min(0) int quantity,
	@Min(0) int sellingPrice,
	MultipartFile image,
	@NotNull boolean packing,
	@NotBlank String author,
	@NotBlank String isbn,
	@NotBlank String publisher,
	String tagList,
	String categoryList
) {
}
