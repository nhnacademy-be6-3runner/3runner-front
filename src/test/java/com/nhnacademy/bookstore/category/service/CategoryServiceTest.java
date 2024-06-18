package com.nhnacademy.bookstore.category.service;

import com.nhnacademy.bookstore.book.category.dto.request.CreateCategoryRequestDto;
import com.nhnacademy.bookstore.book.category.dto.request.UpdateCategoryRequestDto;
import com.nhnacademy.bookstore.book.category.exception.CategoryNotFoundException;
import com.nhnacademy.bookstore.book.category.exception.DuplicateCategoryNameException;
import com.nhnacademy.bookstore.book.category.repository.CategoryRepository;
import com.nhnacademy.bookstore.book.category.service.impl.CategoryServiceImpl;
import com.nhnacademy.bookstore.entity.category.Category;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
    }

    // TODO 상위/하위 생성, 수정 카테고리 테스트 수정

    @DisplayName("상위 카테고리 생성 테스트")
    @Test
    void createParentCategoryTest() {
        CreateCategoryRequestDto dto = CreateCategoryRequestDto.builder()
                .name("new category")
                .parentId(null)
                .build();

        categoryService.createCategory(dto);

        verify(categoryRepository, times(1)).save(any(Category.class));
        Optional<Category> resultCategory = categoryRepository.findByName("new category");
        Assertions.assertTrue(resultCategory.isPresent());
        Assertions.assertNull(resultCategory.get().getParent());
    }

    @DisplayName("하위 카테고리 생성 테스트")
    @Test
    void createChildrenCategoryTest() {
        Category parentCategory = Category.builder()
                .id(1L)
                .name("parent category")
                .build();
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(parentCategory));

        CreateCategoryRequestDto dto = CreateCategoryRequestDto.builder()
                .name("child category")
                .parentId(1L)
                .build();

        categoryService.createCategory(dto);

        verify(categoryRepository, times(1)).save(any(Category.class));
        Optional<Category> resultCategory = categoryRepository.findByName("child category");
        Assertions.assertTrue(resultCategory.isPresent());
        Assertions.assertEquals(parentCategory, resultCategory.get().getParent());
    }

    @DisplayName("카테고리 수정 테스트")
    @Test
    void updateCategoryTest() {
        Category category = Category.builder()
                .id(1L)
                .name("old name")
                .build();
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        UpdateCategoryRequestDto dto = new UpdateCategoryRequestDto("new name", null);

        categoryService.updateCategory(1L, dto);

        verify(categoryRepository, times(1)).save(any(Category.class));
        Assertions.assertEquals("new name", category.getName());
    }

    @DisplayName("카테고리 삭제 테스트")
    @Test
    void deleteCategoryTest() {
        Category category = Category.builder()
                .id(1L)
                .name("category")
                .build();
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        categoryService.deleteCategory(1L);

        verify(categoryRepository, times(1)).delete(any(Category.class));
    }

    @DisplayName("중복된 이름 카테고리 생성 테스트")
    @Test
    void createCategory_DuplicateName_Exception() {
        CreateCategoryRequestDto dto = new CreateCategoryRequestDto("test", null);
        when(categoryRepository.existsByName(any())).thenReturn(true);

        assertThatThrownBy(() -> categoryService.createCategory(dto))
                .isInstanceOf(DuplicateCategoryNameException.class);
    }

    @DisplayName("존재하지 않는 카테고리 수정 테스트")
    @Test
    void updateCategory_CategoryNotFound_Exception() {
        long id = 1L;
        UpdateCategoryRequestDto dto = new UpdateCategoryRequestDto("test", null);
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.updateCategory(id, dto))
                .isInstanceOf(CategoryNotFoundException.class);
    }

    @DisplayName("존재하지 않는 카테고리 삭제 테스트")
    @Test
    void deleteCategory_CategoryNotFound_Exception() {
        long id = 1L;
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.deleteCategory(id))
                .isInstanceOf(CategoryNotFoundException.class);
    }

    // TODO 상위,하위, 전체 카테고리 테스트 추가
}

