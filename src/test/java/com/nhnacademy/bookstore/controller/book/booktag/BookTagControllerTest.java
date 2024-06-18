package com.nhnacademy.bookstore.controller.book.booktag;

import com.nhnacademy.bookstore.Application;
import com.nhnacademy.bookstore.book.bookTag.dto.request.ReadBookIdRequest;
import com.nhnacademy.bookstore.book.bookTag.dto.request.ReadTagRequest;
import com.nhnacademy.bookstore.book.bookTag.dto.response.ReadBookByTagResponse;
import com.nhnacademy.bookstore.book.bookTag.dto.response.ReadTagByBookResponse;
import com.nhnacademy.bookstore.book.bookTag.service.BookTagService;
import com.nhnacademy.bookstore.book.bookTag.controller.BookTagController;
import com.nhnacademy.bookstore.util.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {Application.class})
@AutoConfigureMockMvc
class BookTagControllerTest {

    @Mock
    private BookTagService bookTagService;

    @InjectMocks
    private BookTagController bookTagController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void testGetBookByTagId() {
        // Mock data
        ReadTagRequest tagRequest = ReadTagRequest.builder().tagId(1L).build();
        ReadBookByTagResponse bookResponse = ReadBookByTagResponse.builder()
                .title("Test Book")
                .description("Test Description")
                .publishedDate(null)
                .price(100)
                .account(10)
                .sellingPrice(90)
                .view_count(1000)
                .packing(true)
                .author("Test Author")
                .publisher("Test Publisher")
                .creationDate(null)
                .build();
        Page<ReadBookByTagResponse> mockPage = new PageImpl<>(Collections.singletonList(bookResponse));
        Pageable pageable = PageRequest.of(0, 10);

        // Mock service method
        when(bookTagService.getBookByTagId(any(ReadTagRequest.class), any(Pageable.class))).thenReturn(mockPage);

        // Call controller method
        ApiResponse<Page<ReadBookByTagResponse>> responseEntity = bookTagController.getBookByTagId(tagRequest, 0, 10, null);

        // Verify


        assertEquals(200, responseEntity.getHeader().getResultCode());
        assertEquals("SUCCESS", responseEntity.getHeader().getResultMessage());
        assertTrue(responseEntity.getHeader().isSuccessful());
        assertEquals(mockPage, responseEntity.getBody().getData());
    }



    @Test
    void testGetTagByBookId() {
        // Mock data
        ReadBookIdRequest bookIdRequest = new ReadBookIdRequest(1L);
        Set<ReadTagByBookResponse> mockTags = createMockTagByBookResponseSet();

        // Mock service method
        when(bookTagService.getTagByBookId(any(ReadBookIdRequest.class))).thenReturn(mockTags);

        // Call controller method
        ApiResponse<Set<ReadTagByBookResponse>> responseEntity = bookTagController.getTagByBookId(bookIdRequest);

        // Verify
        assertEquals(200, responseEntity.getHeader().getResultCode());
        assertEquals("SUCCESS", responseEntity.getHeader().getResultMessage());
        assertTrue(responseEntity.getHeader().isSuccessful());
        assertEquals(mockTags, responseEntity.getBody().getData());
    }


    private Set<ReadTagByBookResponse> createMockTagByBookResponseSet() {
        // Create a mock set of tag responses
        Set<ReadTagByBookResponse> mockSet = new HashSet<>();
        mockSet.add(new ReadTagByBookResponse("Tag1"));
        mockSet.add(new ReadTagByBookResponse("Tag2"));
        return mockSet;
    }
}
