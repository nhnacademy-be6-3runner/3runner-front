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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
        duplicateCategoryName(dto.getName());
        Category category;

        // TODO 변경감지
        if (dto.getParentId() != null) {
            // 부모 찾기
            Category parentCategory = categoryRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new CategoryNotFoundException(""));
            // 자식 생성
            Category child = Category.builder()
                    .name(dto.getName())
                    .parent(parentCategory.getParent())
                    .build();
            // 부모 카테고리의 자식 목록에 생성한 자식 카테고리 추가
            parentCategory.addChildren(child);
            // 자식 카테고리 저장
            categoryRepository.save(child);
        } else {
            category = Category.builder()
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
    public Set<CategoryResponse> getCategories() {
        return categoryRepository.findCategories();
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
