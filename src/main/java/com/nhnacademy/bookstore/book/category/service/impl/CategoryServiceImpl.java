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

/**
 * @author 김은비
 */
@Transactional
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public void createCategory(CreateCategoryRequest dto) {
        // 이름 중복 확인
        if (categoryRepository.existsByName(dto.getName())) {
            throw new DuplicateCategoryNameException("중복된 카테고리 이름입니다.");
        }

        Category parent = null;
        if (dto.getParentId() != null) {
            parent = categoryRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new CategoryNotFoundException("존재하지 않는 상위 카테고리입니다."));
        }

        Category category = new Category();
        category.setName(dto.getName());
        category.setParent(parent);

        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(long id, UpdateCategoryRequest dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("카테고리가 존재하지 않습니다."));

        // 이름 중복 확인
        if (!category.getName().equals(dto.getName()) && categoryRepository.existsByName(dto.getName())) {
            throw new DuplicateCategoryNameException("중복된 카테고리 이름입니다.");
        }

        Category parent = null;
        if (dto.getParentId() != null) {
            parent = categoryRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new CategoryNotFoundException("존재하지 않는 상위 카테고리입니다."));
        }

        category.setName(dto.getName());
        category.setParent(parent);

        // category 객체는 이미 영속성 컨텍스트 내에 있으므로, save 호출 없이 자동으로 업데이트됨
    }

    @Override
    public void deleteCategory(long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("카테고리를 찾을 수 없습니다."));

        categoryRepository.delete(category);
    }

    @Override
    public CategoryResponse getCategory(long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("카테고리를 찾을 수 없습니다."));

        return new CategoryResponse(category.getId(), category.getName());
    }

    public List<CategoryResponse> getCategories() {
        return categoryRepository.findCategories();
    }

    @Override
    public List<CategoryResponse> getParentCategories() {
        return categoryRepository.findTopCategories();
    }

    @Override
    public List<CategoryChildrenResponse> getChildrenCategoriesByParentId(long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException("존재하지 않는 카테고리입니다.");
        }
        return categoryRepository.findChildrenCategoriesByParentId(id);
    }
}

