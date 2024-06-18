package com.nhnacademy.bookstore.book.category.service.impl;

import com.nhnacademy.bookstore.book.category.dto.request.CreateCategoryRequestDto;
import com.nhnacademy.bookstore.book.category.dto.request.UpdateCategoryRequestDto;
import com.nhnacademy.bookstore.book.category.dto.response.CategoryChildrenResponseDto;
import com.nhnacademy.bookstore.book.category.dto.response.CategoryResponseDto;
import com.nhnacademy.bookstore.book.category.exception.CategoryNotFoundException;
import com.nhnacademy.bookstore.book.category.exception.DuplicateCategoryNameException;
import com.nhnacademy.bookstore.book.category.repository.CategoryRepository;
import com.nhnacademy.bookstore.book.category.service.CategoryService;
import com.nhnacademy.bookstore.entity.category.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public void createCategory(CreateCategoryRequestDto dto) {
        duplicateCategoryName(dto.getName());

        Category category;
        if (dto.getParentId() != null) {
            Category parentCategory = categoryRepository.findById(dto.getParentId())
                    .orElseThrow(CategoryNotFoundException::new);
            category = Category.builder()
                    .name(dto.getName())
                    .parent(parentCategory)
                    .build();
        } else {
            category = Category.builder()
                    .name(dto.getName())
                    .build();
        }
        categoryRepository.save(category);
    }

    /**
     * 카테고리 수정 메서드
     * @param id 수정할 카테고리 아이디
     * @param dto 수정 내용
     */
    @Override
    public void updateCategory(long id, UpdateCategoryRequestDto dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);

        duplicateCategoryName(dto.getName());

        Category updatedCategory = Category.builder()
                .name(dto.getName())
                .parent(dto.getParentId() != null ? categoryRepository.findById(dto.getParentId())
                        .orElseThrow(CategoryNotFoundException::new) : null)
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
                .orElseThrow(CategoryNotFoundException::new);

        categoryRepository.delete(category);
    }

    /**
     * 모든 카테고리 조회
     * @return 카테고리 list
     */
    public List<CategoryResponseDto> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> new CategoryResponseDto(category.getId(), category.getName(), category.getParent()))
                .collect(Collectors.toList());
    }

    /**
     * 카테고리 + 부모 카테고리 조회
     * @return 해당 키테고리 list
     */
    @Override
    public List<CategoryResponseDto> getParentCategories() {
        List<CategoryResponseDto> parentCategories = categoryRepository.findParentCategories();
        return parentCategories.stream()
                .map(category -> new CategoryResponseDto(category.getId(), category.getName()))
                .collect(Collectors.toList());
    }

    /**
     * 부모 카테고리 아이디로 자식 카테고리 조회
     * @param id 부모 카테고리 아이디
     * @return 자식 카테고리 list
     */
    @Override
    public List<CategoryChildrenResponseDto> getChildrenCategoriesByParentId(long id) {
        List<CategoryChildrenResponseDto> childCategories = categoryRepository.findChildrenCategoriesByParentId(id);
        return childCategories.stream()
                .map(category -> new CategoryChildrenResponseDto(category.getId(), category.getName()))
                .collect(Collectors.toList());
    }

    /**
     * 이름 중복 검사 메서드
     * @param name 검사할 이름
     */
    public void duplicateCategoryName(String name) {
        if (categoryRepository.existsByName(name)) {
            throw new DuplicateCategoryNameException();
        }
    }
}
