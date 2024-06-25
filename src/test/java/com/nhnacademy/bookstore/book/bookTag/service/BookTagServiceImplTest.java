package com.nhnacademy.bookstore.book.bookTag.service;

import com.nhnacademy.bookstore.book.bookTag.dto.request.ReadBookIdRequest;
import com.nhnacademy.bookstore.book.bookTag.dto.request.ReadTagRequest;
import com.nhnacademy.bookstore.book.bookTag.dto.response.ReadBookByTagResponse;
import com.nhnacademy.bookstore.book.bookTag.dto.response.ReadTagByBookResponse;
import com.nhnacademy.bookstore.book.bookTag.repository.BookTagRepository;
import com.nhnacademy.bookstore.book.bookTag.service.Impl.BookTagServiceImpl;
import com.nhnacademy.bookstore.entity.book.Book;
import com.nhnacademy.bookstore.entity.tag.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookTagServiceImplTest {

    @Mock
    private BookTagRepository bookTagRepository;

    @InjectMocks
    private BookTagServiceImpl bookTagService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void readBookByTagId_Success() {
        // Mocking input
        ReadTagRequest tagRequest = new ReadTagRequest(1L, 0, 10, "title");
        Pageable pageable = PageRequest.of(tagRequest.page(), tagRequest.size());

        // Mocking repository response
        Page<Book> mockPage = new PageImpl<>(Collections.singletonList(
                new Book("Sample Book", "Sample Description", ZonedDateTime.now(),
                        100, 50, 80, 500, true, "John Doe","1234567789", "Sample Publisher",null,null,null)
        ));
        when(bookTagRepository.findAllBookIdByTagId(anyLong(), any())).thenReturn(mockPage);

        // Calling the service method
        Page<ReadBookByTagResponse> response = bookTagService.readBookByTagId(tagRequest, pageable);

        // Verifying repository method invocation
        verify(bookTagRepository, times(1)).findAllBookIdByTagId(anyLong(), any());

        // Assertions
        assertFalse(response.isEmpty());
        assertEquals(1, response.getContent().size());
        assertEquals("Sample Book", response.getContent().getFirst().title());
    }

    @Test
    void readTagByBookId_Success() {
        // Mocking input
        ReadBookIdRequest bookIdRequest = new ReadBookIdRequest(1L);
        Tag t = new Tag();
        t.setName("Fantasy");
        // Mocking repository response
        List<Tag> mockSet = new ArrayList<>();
        mockSet.add(t);
        when(bookTagRepository.findAllTagIdByBookId(anyLong())).thenReturn(mockSet);

        // Calling the service method
        List<ReadTagByBookResponse> response = bookTagService.readTagByBookId(bookIdRequest);

        // Verifying repository method invocation
        verify(bookTagRepository, times(1)).findAllTagIdByBookId(anyLong());

        // Assertions
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
        assertEquals("Fantasy", response.iterator().next().name());
    }
}
