package com.nhnacademy.bookstore.book.category.controller;

import com.nhnacademy.bookstore.book.category.dto.request.CreateCategoryRequest;
import com.nhnacademy.bookstore.book.category.dto.request.UpdateCategoryRequest;
import com.nhnacademy.bookstore.book.category.dto.response.CategoryChildrenResponse;
import com.nhnacademy.bookstore.book.category.dto.response.CategoryResponse;
import com.nhnacademy.bookstore.book.category.exception.CreateCategoryRequestException;
import com.nhnacademy.bookstore.book.category.exception.UpdateCategoryRequestException;
import com.nhnacademy.bookstore.book.category.service.CategoryService;
import com.nhnacademy.bookstore.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * Category Controller
 * @author 김은비
 */
@RestController
@RequestMapping(("/categories"))
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    /**
     * 카테고리 생성 컨트롤러
     * @param dto 생성할 내용
     * @param bindingResult 데이터 바인딩 결과
     * @return 카테고리 생성 여부에 대한 api 응답
     * @throws CreateCategoryRequestException 카테고리 생성 요청에 오류가 있는 경우 발생
     */
    @PostMapping
    public ApiResponse<Void> createCategory(@Valid @RequestBody CreateCategoryRequest dto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new CreateCategoryRequestException(bindingResult.getFieldErrors().toString());
        }
        categoryService.createCategory(dto);

        return new ApiResponse<Void>(new ApiResponse.Header(true, 201, "create category"));
    }

    /**
     * 카테고리 수정 컨트롤러
     * @param dto 업데이트할 내용
     * @param categoryId 수정할 카테고리 아이디
     * @param bindingResult 데이터 바인딩 결과
     * @return 카테고리 수정 여부에 대한 api 응답
     * @throws UpdateCategoryRequestException 카테고리 수정 요청에 오류가 있는 경우 발생
     */
    @PutMapping("/{categoryId}")
    public ApiResponse<Void> updateCategory(@Valid @RequestBody UpdateCategoryRequest dto, @PathVariable Long categoryId, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new UpdateCategoryRequestException(bindingResult.getFieldErrors().toString());
        }
        categoryService.updateCategory(categoryId, dto);
        return new ApiResponse<Void>(new ApiResponse.Header(true, 201, "update category"));
    }

    /**
     * 단일 카테고리 조회
     * @param categoryId 조회할 카테고리 ID
     * @return 카테고리 정보
     */
    @GetMapping("/{categoryId}")
    public ApiResponse<CategoryResponse> readCategory(@PathVariable Long categoryId) {
        return ApiResponse.success(categoryService.getCategory(categoryId));
    }

    /**
     * 모든 카테고리 조회
     * @return 모든 카테고리 set
     */
    @GetMapping
    public ApiResponse<Set<CategoryResponse>> readAllCategories() {
        return ApiResponse.success(categoryService.getCategories());
    }


    /**
     * 상위 카테고리 조회
     * @return 상위 카데고리 set
     */
    @GetMapping("/parents")
    public ApiResponse<Set<CategoryResponse>> readAllParentCategories() {
        return ApiResponse.success(categoryService.getParentCategories());
    }

    /**
     * 상위 카테고리 아이디로 하위 카테고리 리스트 조회
     * @param parentId 상위 카테고리 아이디
     * @return 하위 카테고리 set
     */
    @GetMapping("/{parentId}/children")
    public ApiResponse<Set<CategoryChildrenResponse>> readAllChildCategoriesByParentId(@PathVariable Long parentId) {
        return ApiResponse.success(categoryService.getChildrenCategoriesByParentId(parentId));
    }
}
