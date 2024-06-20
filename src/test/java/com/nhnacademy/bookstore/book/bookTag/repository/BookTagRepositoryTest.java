package com.nhnacademy.bookstore.book.bookTag.repository;

import com.nhnacademy.bookstore.book.book.repository.BookRepository;
import com.nhnacademy.bookstore.book.tag.repository.TagRepository;
import com.nhnacademy.bookstore.entity.book.Book;
import com.nhnacademy.bookstore.entity.bookTag.BookTag;
import com.nhnacademy.bookstore.entity.tag.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class BookTagRepositoryTest {

    @Autowired
    private BookTagRepository bookTagRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TagRepository tagRepository;

    private Book book;
    private Tag tag;

    @BeforeEach
    public void setUp() {
        book = new Book();
        book.setTitle("Sample Book");
        book.setDescription("Sample Description");
        book.setPrice(100);
        book.setQuantity(50);
        book.setSellingPrice(80);
        book.setView_count(500);
        book.setPacking(true);
        book.setIsbn("123456789");
        book.setAuthor("John Doe");
        book.setPublisher("Sample Publisher");
        bookRepository.save(book);

        tag = new Tag();
        tag.setName("Sample Tag");
        tagRepository.save(tag);

        BookTag bookTag = new BookTag(book, tag);
        bookTagRepository.save(bookTag);
    }

    @Test
    public void testFindAllBookIdByTagId() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> books = bookTagRepository.findAllBookIdByTagId(tag.getId(), pageable);

        assertThat(books).isNotEmpty();
        assertThat(books.getContent().getFirst().getTitle()).isEqualTo(book.getTitle());
    }

    @Test
    public void testFindAllTagIdByBookId() {
        List<Tag> tags = bookTagRepository.findAllTagIdByBookId(book.getId());

        assertThat(tags).isNotEmpty();
        assertThat(tags.iterator().next().getName()).isEqualTo(tag.getName());
    }
}
