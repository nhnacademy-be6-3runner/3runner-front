package com.nhnacademy.bookstore.book.category.repository;

import com.nhnacademy.bookstore.entity.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * category repository
 * @author 김은비
 */
public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryCustomRepository {
    boolean existsByName(String name);
    Optional<Category> findByName(String name);


}
