package com.nhnacademy.bookstore.book.tag.repository;

import com.nhnacademy.bookstore.entity.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
