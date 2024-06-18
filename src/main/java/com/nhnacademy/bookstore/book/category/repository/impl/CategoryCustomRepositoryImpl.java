package com.nhnacademy.bookstore.book.category.repository.impl;

import com.nhnacademy.bookstore.book.category.dto.response.CategoryChildrenResponseDto;
import com.nhnacademy.bookstore.book.category.dto.response.CategoryParentWithChildrenResponseDto;
import com.nhnacademy.bookstore.book.category.dto.response.CategoryResponseDto;
import com.nhnacademy.bookstore.book.category.repository.CategoryCustomRepository;
import com.nhnacademy.bookstore.entity.category.QCategory;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author 김은비
 *
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
     */
    @Override
    public List<CategoryResponseDto> findCategories() {
        return jpaQueryFactory
                .select(Projections.constructor(CategoryResponseDto.class,
                        qCategory.id,
                        qCategory.name,
                        qCategory.parent))
                .from(qCategory)
                .fetch();
    }

    /**
     * 상위 카테고리 조회
     * @return
     */
    @Override
    public List<CategoryResponseDto> findParentCategories() {
        return jpaQueryFactory
                .select(Projections.constructor(CategoryResponseDto.class,
                        qCategory.id,
                        qCategory.name))
                .from(qCategory)
                .where(qCategory.parent.isNull())
                .fetch();
    }

    /**
     * 상위 카테고리와 해당 하위 카테고리 목록 조회
     * @return
     */
    @Override
    public List<CategoryParentWithChildrenResponseDto> findParentWithChildrenCategories() {
        return jpaQueryFactory
                .select(Projections.constructor(CategoryParentWithChildrenResponseDto.class,
                        qCategory.id,
                        qCategory.name,
                        qCategory.children.as("children")))
                .from(qCategory)
                .where(qCategory.parent.isNull())
                .fetch();
    }

    /**
     * 상위 카테고리 아이디로 하위 카테고리 조회
     * @param id 상위 카테고리 아이디
     * @return 하위 카테고리 list
     */
    @Override
    public List<CategoryChildrenResponseDto> findChildrenCategoriesByParentId(Long id) {
        return jpaQueryFactory
                .select(Projections.constructor(CategoryChildrenResponseDto.class,
                        qCategory.id,
                        qCategory.name))
                .from(qCategory)
                .where(qCategory.parent.id.eq(id))
                .fetch();
    }
}
