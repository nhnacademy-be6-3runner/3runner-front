package com.nhnacademy.front.book.categroy.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhnacademy.front.book.categroy.controller.feign.CategoryClient;
import com.nhnacademy.front.book.categroy.dto.response.CategoryChildrenResponse;
import com.nhnacademy.front.book.categroy.exception.NotFindCategoryException;
import com.nhnacademy.front.util.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author 한민기
 *

 */
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

	private final CategoryClient categoryClient;

	/**
	 *  * 책 등록 화면에서 모든 카테고리 불러오기
	 * @return 카테고리를 자녀 카테고리 형식으로 불러옴
	 */
	@GetMapping
	public List<CategoryChildrenResponse> readAllCategories() {

		ApiResponse<List<CategoryChildrenResponse>> getResponse = categoryClient.readAllTagSet();

		log.info(getResponse.getBody().getData().toString());
		if (!getResponse.getHeader().isSuccessful()) {
			throw new NotFindCategoryException();
		}

		return getResponse.getBody().getData();
	}

}
