package com.nhnacademy.front.book.book.dto.response;

import com.nhnacademy.front.book.categroy.dto.response.CategoryParentWithChildrenResponse;
import com.nhnacademy.front.book.tag.dto.response.ReadTagByBookResponse;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.Builder;

/**
 * book response form validate.
 *
 * @author 김병우
 *
 * @param title                제목
 * @param description        설명
 * @param publishedDate        출판일
 * @param price                적정가
 * @param quantity            수량
 * @param sellingPrice        판매가
 * @param viewCount            조회수
 * @param packing            선물포장 여부
 * @param author            작가
 * @param isbn                isbn13
 * @param publisher            출판사
 * @param imagePath            메인 이미지의 파일 위치
 * @param categoryList        카테고리 리스트
 * @param tagList            태그 리스트
 */
@Builder
public record UserReadBookResponse(

	long id,
	String title,
	String description,
	ZonedDateTime publishedDate,
	int price,
	int quantity,
	int sellingPrice,
	int viewCount,
	boolean packing,
	String author,
	String isbn,
	String publisher,
	String imagePath,
	List<CategoryParentWithChildrenResponse> categoryList,
	List<ReadTagByBookResponse> tagList

) {
}
