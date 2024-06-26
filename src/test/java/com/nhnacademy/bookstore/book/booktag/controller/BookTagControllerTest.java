package com.nhnacademy.bookstore.book.booktag.controller;

import com.nhnacademy.bookstore.book.bookTag.controller.BookTagController;
import com.nhnacademy.bookstore.book.booktag.dto.request.ReadBookIdRequest;
import com.nhnacademy.bookstore.book.booktag.dto.request.ReadTagRequest;
import com.nhnacademy.bookstore.book.booktag.dto.response.ReadBookByTagResponse;
import com.nhnacademy.bookstore.book.booktag.dto.response.ReadTagByBookResponse;
import com.nhnacademy.bookstore.book.booktag.service.BookTagService;
import com.nhnacademy.bookstore.util.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.time.ZonedDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookTagControllerTest {

    @Mock
    private BookTagService bookTagService;

    @InjectMocks
    private BookTagController bookTagController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void readBookByTagId_Success() {
        // Mocking input
        ReadTagRequest tagRequest = new ReadTagRequest(1L, 1, 10, "title");

        // Mocking service response
        Page<ReadBookByTagResponse> mockPage = new PageImpl<>(Collections.singletonList(
                new ReadBookByTagResponse("Sample Book", "Sample Description",
                        ZonedDateTime.now(), 100, 50, 80, 500, true,
                        "John Doe", "Sample Publisher", ZonedDateTime.now())
        ));
        when(bookTagService.readBookByTagId(any(), any())).thenReturn(mockPage);

        // Calling the controller method
        ApiResponse<Page<ReadBookByTagResponse>> response = bookTagController.readBookByTagId(tagRequest, mock(BindingResult.class));

        // Verifying service method invocation
        verify(bookTagService, times(1)).readBookByTagId(any(), any());

        // Assertions
        assertEquals(HttpStatus.OK, HttpStatus.valueOf(response.getHeader().getResultCode()));
        assertEquals(1, response.getBody().getData().getContent().size());
        assertEquals("Sample Book", response.getBody().getData().getContent().getFirst().title());
    }

    @Test
    void readTagByBookId_Success() {
        // Mocking input
        ReadBookIdRequest bookIdRequest = new ReadBookIdRequest(1L);

        // Mocking service response
        List<ReadTagByBookResponse> mockSet = new ArrayList<>();
        mockSet.add(new ReadTagByBookResponse("Fantasy"));

        when(bookTagService.readTagByBookId(any())).thenReturn(mockSet);

        // Calling the controller method
        ApiResponse<List<ReadTagByBookResponse>> response = bookTagController.readTagByBookId(bookIdRequest, mock(BindingResult.class));

        // Verifying service method invocation
        verify(bookTagService, times(1)).readTagByBookId(any());

        // Assertions
        assertEquals(HttpStatus.OK, HttpStatus.valueOf(response.getHeader().getResultCode()));
        assertEquals(1, response.getBody().getData().size());
        assertEquals("Fantasy", response.getBody().getData().iterator().next().name());
    }
}
