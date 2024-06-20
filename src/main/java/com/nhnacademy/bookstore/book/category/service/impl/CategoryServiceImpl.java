package com.nhnacademy.bookstore.book.category.service.impl;

import com.nhnacademy.bookstore.book.category.dto.request.CreateCategoryRequest;
import com.nhnacademy.bookstore.book.category.dto.request.UpdateCategoryRequest;
import com.nhnacademy.bookstore.book.category.dto.response.CategoryChildrenResponse;
import com.nhnacademy.bookstore.book.category.dto.response.CategoryResponse;
import com.nhnacademy.bookstore.book.category.exception.CategoryNotFoundException;
import com.nhnacademy.bookstore.book.category.exception.DuplicateCategoryNameException;
import com.nhnacademy.bookstore.book.category.repository.CategoryRepository;
import com.nhnacademy.bookstore.book.category.service.CategoryService;
import com.nhnacademy.bookstore.entity.category.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author 김은비
 */
@Transactional
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    /**
     * 카테고리 생성 메서드
     * dto parentId = null 이면 최상위 카테고리
     * @param dto 생성 내용
     */
    @Override
    public void createCategory(CreateCategoryRequest dto) {
        // 이름 중복 확인
        duplicateCategoryName(dto.getName());

        // 부모가 있으면
        if (dto.getParentId() != null) {
            // dto parentId 존재하나? -> 부모 객체 생성
            Category parentCategory = categoryRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new CategoryNotFoundException("존재하지 않는 상위 카테고리입니다."));
            Category child = Category.builder()
                    .name(dto.getName())
                    .parent(parentCategory) // 부모 지정
                    .build();
            categoryRepository.save(child);
            parentCategory.addChildren(child);
        } else {
            Category category = Category.builder()
                    .name(dto.getName())
                    .build();
            categoryRepository.save(category);
        }
    }

    /**
     * 카테고리 수정 메서드
     * @param id 수정할 카테고리 아이디
     * @param dto 수정 내용
     */
    @Override
    public void updateCategory(long id, UpdateCategoryRequest dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("카테고리가 존재하지 않습니다."));

        duplicateCategoryName(dto.getName());

        Category updatedCategory = Category.builder()
                .id(category.getId())
                .name(dto.getName())
                .parent(dto.getParentId() != null ? categoryRepository.findById(dto.getParentId())
                        .orElseThrow(() -> new CategoryNotFoundException("카테고리가 존재하지 않습니다.")) : null)
                .build();
        categoryRepository.save(updatedCategory);
    }

    /**
     * 카테고리 삭제 메서드
     * @param id 삭제할 카테고리 아이디
     */
    @Override
    public void deleteCategory(long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("카테고리를 찾을 수 없습니다."));

        categoryRepository.delete(category);
    }

    /**
     * 카테고리 단건 조회
     * @param id 조회할 카테고리 아이디
     * @return 조회된 카테고리
     */
    @Override
    public CategoryResponse getCategory(long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("카테고리를 찾을 수 없습니다."));

        return new CategoryResponse(category.getId(), category.getName());
    }

    /**
     * 모든 카테고리 조회
     * @return 카테고리 set
     */
    public List<CategoryResponse> getCategories() {
        List<CategoryResponse> categories = categoryRepository.findCategories();
        List<CategoryResponse> categoryResponses = new ArrayList<>();

        for (CategoryResponse category : categories) {
            CategoryResponse categoryResponse = CategoryResponse.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .parent(category.getParent() != null
                            ? new CategoryResponse(
                            category.getParent().getId(),
                            category.getParent().getName(),
                            category.getParent().getParent()).getParent()
                            : null)
                    .build();
            categoryResponses.add(categoryResponse);
        }
        return categoryResponses;
    }


    /**
     * 상위 카테고리 조회
     * @return 해당 키테고리 set
     */
    @Override
    public Set<CategoryResponse> getParentCategories() {
        return categoryRepository.findParentCategories();
    }


    /**
     * 부모 카테고리 아이디로 자식 카테고리 조회
     * @param id 부모 카테고리 아이디
     * @return 자식 카테고리 set
     */
    @Override
    public Set<CategoryChildrenResponse> getChildrenCategoriesByParentId(long id) {
        return categoryRepository.findChildrenCategoriesByParentId(id);
    }


    /**
     * 이름 중복 검사 메서드
     * @param name 검사할 이름
     */
    public void duplicateCategoryName(String name) {
        if (categoryRepository.existsByName(name)) {
            throw new DuplicateCategoryNameException("중복된 카테고리 이름입니다.");
        }
    }
}
