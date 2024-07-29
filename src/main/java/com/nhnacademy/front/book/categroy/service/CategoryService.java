package com.nhnacademy.front.book.categroy.service;

import com.nhnacademy.front.book.categroy.dto.response.CategoryChildrenResponse;
import java.util.List;

/**
 * 카테고리 관련 서비스.
 *
 * @author 한민기
 */
public interface CategoryService {

	/**
	 * 전체 카테고리 조회하는 서비스.
	 *
	 * @return 전체 카테고리를 부모 밑에 자식을 넣어 조회
	 */
	List<CategoryChildrenResponse> readAllCategoryList();

	/**
	 * 관리자가 카테고리 삭제 메소드.
	 *
	 * @param categoryId 카테고리 아이디
	 */
	void deleteCategory(Long categoryId);

	/**
	 * 카테고리 추가 메소드.
	 *
	 * @param name        추가할 카테고리 이름
	 * @param parent    추가할 카테고리의 부모 id
	 */
	void addCategory(String name, Long parent);
}
