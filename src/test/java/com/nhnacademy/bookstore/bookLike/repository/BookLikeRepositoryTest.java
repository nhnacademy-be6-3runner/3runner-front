package com.nhnacademy.bookstore.bookLike.repository;

import com.nhnacademy.bookstore.book.bookLike.repository.BookLikeCustomRepository;
import com.nhnacademy.bookstore.book.bookLike.repository.BookLikeRepository;
import com.nhnacademy.bookstore.entity.book.Book;
import com.nhnacademy.bookstore.entity.member.Member;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(BookLikeCustomRepository.class)
public class BookLikeRepositoryTest {
  @Autowired
  private EntityManager entityManager;

  @Autowired
  private BookLikeRepository bookLikeRepository;

  private Member member;
  private Book book1;
  private Book book2;


}
