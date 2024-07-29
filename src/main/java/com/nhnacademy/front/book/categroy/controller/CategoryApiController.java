package com.nhnacademy.front.book.categroy.controller;

import com.nhnacademy.front.book.categroy.dto.response.CategoryChildrenResponse;
import com.nhnacademy.front.book.categroy.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 카테고리 컨트롤러.
 *
 * @author 한민기
 *
 */
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryApiController {

	private final CategoryService categoryService;

	/**
	 *  * 책 등록 화면에서 모든 카테고리 불러오기.
	 *
	 * @return 카테고리를 자녀 카테고리 형식으로 불러옴
	 */
	@GetMapping
	public List<CategoryChildrenResponse> readAllCategories() {
		return categoryService.readAllCategoryList();
	}

}
