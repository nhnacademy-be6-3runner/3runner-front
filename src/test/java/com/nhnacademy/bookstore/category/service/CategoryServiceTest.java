package com.nhnacademy.bookstore.category.service;

import com.nhnacademy.bookstore.book.category.dto.request.CreateCategoryRequest;
import com.nhnacademy.bookstore.book.category.dto.request.UpdateCategoryRequest;
import com.nhnacademy.bookstore.book.category.dto.response.CategoryChildrenResponse;
import com.nhnacademy.bookstore.book.category.dto.response.CategoryParentWithChildrenResponse;
import com.nhnacademy.bookstore.book.category.dto.response.CategoryResponse;
import com.nhnacademy.bookstore.book.category.exception.CategoryNotFoundException;
import com.nhnacademy.bookstore.book.category.exception.DuplicateCategoryNameException;
import com.nhnacademy.bookstore.book.category.repository.CategoryRepository;
import com.nhnacademy.bookstore.book.category.service.impl.CategoryServiceImpl;
import com.nhnacademy.bookstore.entity.category.Category;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    Category parent;

    @BeforeEach
    void setUp() {

    }

    @DisplayName("상위 카테고리 생성 테스트")
    @Test
    void createTopLevelCategory() {
        CreateCategoryRequest dto = new CreateCategoryRequest("상위 카테고리", null);
        Category category = new Category();
        category.setName("상위 카테고리");

        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        categoryService.createCategory(dto);
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @DisplayName("카테고리 삭제 테스트")
    @Test
    void deleteCategory() {
        Category category = new Category();
        category.setName("category");

        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));

        categoryService.deleteCategory(category.getId());

        verify(categoryRepository, times(1)).delete(any(Category.class));
    }

    @DisplayName("중복된 이름 카테고리 생성 테스트")
    @Test
    void createCategory_DuplicateName_Exception() {
        CreateCategoryRequest dto = new CreateCategoryRequest("test", null);
        when(categoryRepository.existsByName(any())).thenReturn(true);

        assertThatThrownBy(() -> categoryService.createCategory(dto))
                .isInstanceOf(DuplicateCategoryNameException.class);
    }

    @DisplayName("존재하지 않는 카테고리 수정 테스트")
    @Test
    void updateCategory_CategoryNotFound_Exception() {
        long id = 1L;
        UpdateCategoryRequest dto = new UpdateCategoryRequest("test", null);
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

    @Test
    void testGetCategoriesWithChildren() {
        Category parentCategory = new Category();
        parentCategory.setId(1L);
        parentCategory.setName("Parent Category");

        Category childCategory = new Category();
        childCategory.setId(2L);
        childCategory.setName("Child Category");
        childCategory.setParent(parentCategory);

        parentCategory.setChildren(Collections.singletonList(childCategory));

        when(categoryRepository.findTopCategories()).thenReturn(Collections.singletonList(new CategoryResponse(1L, "Parent Category")));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(parentCategory));

        List<CategoryParentWithChildrenResponse> result = categoryService.getCategoriesWithChildren();

        assertNotNull(result);
        assertEquals(1, result.size());
        CategoryParentWithChildrenResponse parentResponse = result.get(0);
        assertEquals(1L, parentResponse.getId());
        assertEquals("Parent Category", parentResponse.getName());
        assertNotNull(parentResponse.getChildrenList());
        assertEquals(1, parentResponse.getChildrenList().size());
        CategoryChildrenResponse childResponse = parentResponse.getChildrenList().get(0);
        assertEquals(2L, childResponse.getId());
        assertEquals("Child Category", childResponse.getName());

        verify(categoryRepository, times(1)).findTopCategories();
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void testGetCategoriesWithChildren_CategoryNotFound() {

        when(categoryRepository.findTopCategories()).thenReturn(Collections.singletonList(new CategoryResponse(1L, "Parent Category")));
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());


        assertThrows(CategoryNotFoundException.class, () -> categoryService.getCategoriesWithChildren());

        verify(categoryRepository, times(1)).findTopCategories();
        verify(categoryRepository, times(1)).findById(1L);
    }
}
