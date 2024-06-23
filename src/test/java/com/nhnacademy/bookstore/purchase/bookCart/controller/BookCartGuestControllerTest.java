package com.nhnacademy.bookstore.purchase.bookCart.controller;

import com.nhnacademy.bookstore.purchase.bookCart.dto.request.CreateBookCartGuestRequest;
import com.nhnacademy.bookstore.purchase.bookCart.dto.request.UpdateBookCartGuestRequest;
import com.nhnacademy.bookstore.purchase.bookCart.service.BookCartGuestService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@WebMvcTest(BookCartGuestController.class)
class BookCartGuestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookCartGuestService bookCartGuestService;

    @Test
    void testGetCart() throws Exception {
        when(bookCartGuestService.readAllBookCart(anyLong())).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/bookstore/carts")
                        .cookie(new Cookie("cartId", "1"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.header.successful").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.data").isArray());
    }

    @Test
    void testAddCart() throws Exception {
        CreateBookCartGuestRequest request = new CreateBookCartGuestRequest(1L, 1);
        when(bookCartGuestService.createBookCart(anyLong(), anyLong(), anyInt())).thenReturn(1L);

        mockMvc.perform(MockMvcRequestBuilders.post("/bookstore/carts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"bookId\":1,\"quantity\":1}")
                        .cookie(new Cookie("cartId", "1"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.header.successful").value(true));

        verify(bookCartGuestService, times(1)).createBookCart(anyLong(), anyLong(), anyInt());
    }

    @Test
    void testAddCartWithValidationError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/bookstore/carts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"bookId\":1}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testUpdateCart() throws Exception {
        UpdateBookCartGuestRequest request = new UpdateBookCartGuestRequest(1L, 1);

        mockMvc.perform(MockMvcRequestBuilders.put("/bookstore/carts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"bookId\":1,\"quantity\":1}")
                        .cookie(new Cookie("cartId", "1"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.header.successful").value(true));

        verify(bookCartGuestService, times(1)).updateBookCart(anyLong(), anyLong(), anyInt());
    }

    @Test
    void testUpdateCartWithValidationError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/bookstore/carts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"quantity\":1}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
  
}