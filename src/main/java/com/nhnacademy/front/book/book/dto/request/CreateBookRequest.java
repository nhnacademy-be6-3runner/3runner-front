package com.nhnacademy.front.book.book.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.Builder;

/**
 * 책 생성 관련 레코드.
 *
 * @param title 제목
 * @param description 설명
 * @param publishedDate 출판일
 * @param price 정가
 * @param quantity 수량
 * @param sellingPrice 판매가격
 * @param packing 포장 가능 여부
 * @param author 작가
 * @param isbn 책의 고유 isbn
 * @param publisher 출판사
 * @param imageName 책의 메인 이미지
 * @param imageList 책에 소속된 이미지
 * @param tagIds 책의 태그
 * @param categoryIds 책의 카테고리
 */
@Builder
public record CreateBookRequest(
	@NotBlank(message = "title is mandatory") String title,
	@NotBlank(message = "description is mandatory") String description,
	@NotNull(message = "publishedDate is mandatory") ZonedDateTime publishedDate,
	@Min(value = 0, message = "price must be bigger then 0") int price,
	@Min(value = 0, message = "quantity must be bigger then 0") int quantity,
	@Min(value = 0, message = "sellingPrice must be bigger then 0") int sellingPrice,
	@NotNull(message = "packing is mandatory") boolean packing,
	@NotBlank(message = "author is mandatory") String author,
	@NotBlank(message = "isbn is mandatory") String isbn,
	@NotBlank(message = "publisher is mandatory") String publisher,
	String imageName,
	List<String> imageList,
	List<Long> tagIds,
	List<Long> categoryIds

) {
}
