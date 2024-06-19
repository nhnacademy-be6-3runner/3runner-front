package com.nhnacademy.bookstore.book.category.repository.impl;

import com.nhnacademy.bookstore.book.category.dto.response.CategoryChildrenResponse;
import com.nhnacademy.bookstore.book.category.dto.response.CategoryParentWithChildrenResponse;
import com.nhnacademy.bookstore.book.category.dto.response.CategoryResponse;
import com.nhnacademy.bookstore.book.category.repository.CategoryCustomRepository;
import com.nhnacademy.bookstore.entity.category.QCategory;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * query dsl 인터페이스 구현체
 * @author 김은비
 */
@Slf4j
@Repository
public class CategoryCustomRepositoryImpl implements CategoryCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QCategory qCategory = QCategory.category;

    public CategoryCustomRepositoryImpl(EntityManager entityManager) {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    /**
     * 모든 카테고리 조회
     * @return 모든 카테고리 list
     */
    @Override
    public Set<CategoryResponse> findCategories() {
        List<CategoryResponse> categoryResponses = jpaQueryFactory
                .select(Projections.constructor(CategoryResponse.class,
                        qCategory.id,
                        qCategory.name,
                        qCategory.parent))
                .from(qCategory)
                .fetch();
        return new HashSet<>(categoryResponses);
    }


    /**
     * 상위 카테고리 조회
     * @return 상위 카테고리 list
     */
    @Override
    public Set<CategoryResponse> findParentCategories() {
        List<CategoryResponse> parentCategories = jpaQueryFactory
                .select(Projections.constructor(CategoryResponse.class,
                        qCategory.id,
                        qCategory.name))
                .from(qCategory)
                .where(qCategory.parent.isNull())
                .fetch();

        return new HashSet<>(parentCategories);
    }

    /**
     * 상위 카테고리와 해당 하위 카테고리 목록 조회
     * @return 상위(하위) 카테고리 list
     */
    @Override
    public Set<CategoryParentWithChildrenResponse> findParentWithChildrenCategories() {
        List<CategoryParentWithChildrenResponse> parentWithChildrenCategories = jpaQueryFactory
                .select(Projections.constructor(CategoryParentWithChildrenResponse.class,
                        qCategory.id,
                        qCategory.name,
                        qCategory.children.as("children")))
                .from(qCategory)
                .where(qCategory.parent.isNull())
                .fetch();

        return new HashSet<>(parentWithChildrenCategories);
    }

    /**
     * 상위 카테고리 아이디로 하위 카테고리 조회
     * @param id 상위 카테고리 아이디
     * @return 하위 카테고리 list
     */
    public Set<CategoryChildrenResponse> findChildrenCategoriesByParentId(Long id) {
        List<CategoryChildrenResponse> childrenCategories = jpaQueryFactory
                .select(Projections.constructor(CategoryChildrenResponse.class,
                        qCategory.id,
                        qCategory.name))
                .from(qCategory)
                .where(qCategory.parent.id.eq(id))
                .fetch();

        return new HashSet<>(childrenCategories);
    }
}
