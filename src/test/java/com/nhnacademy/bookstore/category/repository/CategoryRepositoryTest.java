package com.nhnacademy.bookstore.category.repository;

import com.nhnacademy.bookstore.book.category.dto.response.CategoryChildrenResponse;
import com.nhnacademy.bookstore.book.category.dto.response.CategoryResponse;
import com.nhnacademy.bookstore.book.category.repository.CategoryRepository;
import com.nhnacademy.bookstore.book.category.repository.impl.CategoryCustomRepositoryImpl;
import com.nhnacademy.bookstore.entity.category.Category;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Slf4j
@DataJpaTest
@Import(CategoryCustomRepositoryImpl.class)
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;
    private List<Category> categoryList;
    private Category category;

    @BeforeEach
    void setUp() {
        Category parent1 = Category.builder()
                .name("부모 카데고리1")
                .parent(null)
                .build();
        Category parent2 = Category.builder()
                .name("부모 카테고리2")
                .parent(null)
                .build();
        Category children1 = Category.builder()
                .name("자식 카데고리1")
                .parent(parent1)
                .build();
        Category children2 = Category.builder()
                .name("자식 카데고리2")
                .parent(parent1)
                .build();
        Category children3 = Category.builder()
                .name("자식 카데고리3")
                .parent(parent2)
                .build();
        this.categoryList = List.of(parent1, parent2, children1, children2, children3);
    }

    @DisplayName("카테고리 저장 테스트")
    @Test
    void saveParentCategoryTest() {
        category = Category.builder()
                .name("카테고리")
                .build();
        categoryRepository.save(category);
        Optional<Category> savedCategory = categoryRepository.findById(category.getId());

        savedCategory.get();
        Assertions.assertEquals("카테고리", savedCategory.get().getName());
        Assertions.assertNull(savedCategory.get().getParent());
    }

    @DisplayName("자식 카테고리 저장 테스트")
    @Test
    void saveChildCategoryTest() {
        Category parent = Category.builder()
                .name("부모 카테고리")
                .parent(null)
                .build();
        Category child = Category.builder()
                .name("자식 카테고리")
                .parent(parent)
                .build();

        categoryRepository.save(parent);
        categoryRepository.save(child);

        Optional<Category> foundParent = categoryRepository.findById(parent.getId());
        Optional<Category> foundChild = categoryRepository.findById(child.getId());

        Assertions.assertTrue(foundParent.isPresent());
        Assertions.assertTrue(foundChild.isPresent());

        Assertions.assertEquals("부모 카테고리", foundParent.get().getName());
        Assertions.assertEquals("자식 카테고리", foundChild.get().getName());
        Assertions.assertEquals(parent, foundChild.get().getParent());
    }


    @DisplayName("카테고리 이름 조회 테스트")
    @Test
    void findByCategoryName() {
        category = Category.builder()
                .name("카테고리")
                .build();
        categoryRepository.save(category);
        Optional<Category> result = categoryRepository.findByName("카테고리");
        Assertions.assertEquals(category.getName(), result.get().getName());
    }


    @DisplayName("최상위 카테고리 조회 테스트")
    @Test
    void findAllParentCategories() {
        categoryRepository.saveAll(categoryList);
        log.info("size = {}", categoryList.size()); // size = 5
        log.info("{}", categoryList);
        Set<CategoryResponse> parentCategories = categoryRepository.findParentCategories();
        log.info("size = {}", parentCategories.size());

        Assertions.assertEquals(2, parentCategories.size());
    }
}
