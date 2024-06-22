package com.nhnacademy.bookstore.category.repository;

import com.nhnacademy.bookstore.book.category.dto.response.CategoryChildrenResponse;
import com.nhnacademy.bookstore.book.category.dto.response.CategoryParentWithChildrenResponse;
import com.nhnacademy.bookstore.book.category.dto.response.CategoryResponse;
import com.nhnacademy.bookstore.book.category.repository.CategoryRepository;
import com.nhnacademy.bookstore.book.category.repository.impl.CategoryCustomRepositoryImpl;
import com.nhnacademy.bookstore.entity.category.Category;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Slf4j
@DataJpaTest
@Import(CategoryCustomRepositoryImpl.class)
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;
    private List<Category> categoryList;
    private Category category;

    @Autowired
    private EntityManager entityManager;

    private JPAQueryFactory queryFactory;

    @BeforeEach
    void setUp() {
        queryFactory = new JPAQueryFactory(entityManager);

        Category parent1 = new Category();
        parent1.setName("부모 카테고리1");

        Category parent2 = new Category();
        parent2.setName("부모 카테고리2");

        Category children1 = new Category();
        children1.setName("자식 카테고리1");
        children1.setParent(parent1);

        Category children2 = new Category();
        children2.setName("자식 카테고리2");
        children2.setParent(parent1);

        Category children3 = new Category();
        children3.setName("자식 카테고리3");
        children3.setParent(parent2);

        parent1.addChildren(children1);
        parent1.addChildren(children2);
        parent2.addChildren(children3);

        this.categoryList = List.of(parent1, parent2, children1, children2, children3);
    }

    @DisplayName("카테고리 저장 테스트")
    @Test
    void saveParentCategoryTest() {
        Category category = new Category();
        category.setName("카테고리");

        categoryRepository.save(category);
        Optional<Category> savedCategory = categoryRepository.findById(category.getId());

        Assertions.assertTrue(savedCategory.isPresent());
        Assertions.assertEquals("카테고리", savedCategory.get().getName());
        Assertions.assertNull(savedCategory.get().getParent());
    }

    @DisplayName("자식 카테고리 저장 테스트")
    @Test
    void saveChildCategoryTest() {
        Category parent = new Category();
        parent.setName("부모 카테고리");

        Category child = new Category();
        child.setName("자식 카테고리");
        child.setParent(parent);

        categoryRepository.save(parent);
        categoryRepository.save(child);
        parent.addChildren(child);

        Optional<Category> foundParent = categoryRepository.findById(parent.getId());
        Optional<Category> foundChild = categoryRepository.findById(child.getId());

        Assertions.assertTrue(foundParent.isPresent());
        Assertions.assertTrue(foundChild.isPresent());
        log.info("부모 카테고리 : {}", foundParent.get());
        log.info("자식 카테고리 : {}", foundChild.get());

        Assertions.assertEquals("부모 카테고리", foundParent.get().getName());
        Assertions.assertEquals("자식 카테고리", foundChild.get().getName());
        Assertions.assertEquals(parent, foundChild.get().getParent());

        // 부모-자식이 제대로 설정됐는지 확인
        Assertions.assertTrue(foundParent.get().getChildren().stream()
                .anyMatch(c -> c.getId() == foundChild.get().getId()));
    }

    @DisplayName("카테고리 이름 조회 테스트")
    @Test
    void findByCategoryNameTest() {
        Category category = new Category();
        category.setName("카테고리");

        categoryRepository.save(category);
        Optional<Category> result = categoryRepository.findByName("카테고리");

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(category.getName(), result.get().getName());
    }

    @DisplayName("모든 카테고리 조회 테스트")
    @Test
    void findCategoriesTest() {
        categoryRepository.saveAll(categoryList);

        List<CategoryResponse> categories = categoryRepository.findCategories();
        log.info("저장된 카테고리 목록 : {}", categories);

        Assertions.assertNotNull(categories);
        Assertions.assertEquals(5, categories.size());
    }

    @DisplayName("최상위 카테고리 조회 테스트")
    @Test
    void findAllParentCategoriesTest() {
        categoryRepository.saveAll(categoryList);
        log.info("size = {}", categoryList.size()); // size = 5
        log.info("{}", categoryList);

        List<CategoryResponse> parentCategories = categoryRepository.findTopCategories();
        log.info("size = {}", parentCategories.size());

        Assertions.assertEquals(2, parentCategories.size());
    }

    @DisplayName("계층 카테고리 조회 테스트")
    @Test
    void findParentWithChildrenCategoriesTest() {
        categoryRepository.saveAll(categoryList);
        List<CategoryParentWithChildrenResponse> parentWithChildrenCategories = categoryRepository.findParentWithChildrenCategories();

        Assertions.assertEquals(2, parentWithChildrenCategories.size());

        for (CategoryParentWithChildrenResponse parentWithChildren : parentWithChildrenCategories) {
            if (parentWithChildren.getName().equals("부모 카테고리1")) {
                List<CategoryChildrenResponse> children1 = parentWithChildren.getChildrenList();
                Assertions.assertEquals(2, children1.size());
                for (CategoryChildrenResponse child : children1) {
                    Assertions.assertTrue(
                            child.getName().equals("자식 카테고리1") || child.getName().equals("자식 카테고리2"));
                }
            } else if (parentWithChildren.getName().equals("부모 카테고리2")) {
                List<CategoryChildrenResponse> children2 = parentWithChildren.getChildrenList();
                Assertions.assertEquals(1, children2.size());
                for (CategoryChildrenResponse child : children2) {
                    Assertions.assertEquals("자식 카테고리3", child.getName());
                }
            } else {
                Assertions.fail("예상하지 못한 부모 카테고리 이름: " + parentWithChildren.getName());
            }
        }
    }
}
