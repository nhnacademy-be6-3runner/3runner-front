package com.nhnacademy.bookstore.purchase.purchase.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.bookstore.book.bookTag.dto.request.ReadBookIdRequest;
import com.nhnacademy.bookstore.book.bookTag.dto.response.ReadTagByBookResponse;
import com.nhnacademy.bookstore.member.member.service.MemberService;
import com.nhnacademy.bookstore.purchase.purchase.dto.request.CreatePurchaseRequest;
import com.nhnacademy.bookstore.purchase.purchase.dto.request.UpdatePurchaseMemberRequest;
import com.nhnacademy.bookstore.purchase.purchase.dto.response.ReadPurchaseResponse;
import com.nhnacademy.bookstore.purchase.purchase.service.PurchaseMemberService;
import com.nhnacademy.bookstore.util.ApiResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class PurchaseMemberControllerTest {

    @Mock
    private PurchaseMemberService purchaseMemberService;

    @Mock
    private MemberService memberService;

    @InjectMocks
    private PurchaseMemberController purchaseMemberController;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testReadPurchase() throws Exception {
        ReadPurchaseResponse mockResponse = ReadPurchaseResponse.builder()
            .id(1L)
            .orderNumber(UUID.randomUUID())
            .status(null)
            .deliveryPrice(100)
            .totalPrice(5000)
            .createdAt(ZonedDateTime.now())
            .road("Test Road")
            .password("test")
            .memberType(null)
            .build();

        when(purchaseMemberService.readPurchase(anyLong(), anyLong())).thenReturn(mockResponse);

        mockMvc = MockMvcBuilders.standaloneSetup(purchaseMemberController).build();

        mockMvc.perform(get("/members/purchases/{purchaseId}", 1L)
                .header("Member-Id", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(1L)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.header.resultCode").value(200))
            .andExpect(jsonPath("$.header.successful").value(true))
            .andExpect(jsonPath("$.body.data.deliveryPrice").value(100))
            .andExpect(jsonPath("$.body.data.id").value(1));

    }

    @Test
    public void testReadPurchases() throws Exception {
        ReadPurchaseResponse mockResponse = ReadPurchaseResponse.builder()
            .id(1L)
            .orderNumber(UUID.randomUUID())
            .status(null)
            .deliveryPrice(100)
            .totalPrice(5000)
            .createdAt(ZonedDateTime.now())
            .road("Test Road")
            .password("test")
            .memberType(null)
            .build();

        Page<ReadPurchaseResponse> mockPage = new PageImpl<>(Collections.singletonList(mockResponse));
        Pageable pageable = PageRequest.of(0, 10);

        // Mocking the service method behavior
        when(memberService.getPurchasesByMemberId(anyLong(), any(Pageable.class))).thenReturn(mockPage);

        ApiResponse<Page<ReadPurchaseResponse>> response = purchaseMemberController.readPurchases(1L, 1, 10, null);

        // Mocking the request
        assertEquals(HttpStatus.OK, HttpStatus.valueOf(response.getHeader().getResultCode()));
        assertEquals(1, response.getBody().getData().getSize());
        assertEquals(mockResponse.orderNumber().toString(), response.getBody().getData().iterator().next().orderNumber().toString());
    }

    @Test
    public void testCreatePurchase() throws Exception {
        CreatePurchaseRequest request = CreatePurchaseRequest.builder()
            .deliveryPrice(100)
            .totalPrice(5000)
            .road("Test Road")
            .password("test")
            .build();

        mockMvc = MockMvcBuilders.standaloneSetup(purchaseMemberController).build();

        mockMvc.perform(post("/members/purchases")
                .header("Member-Id", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.header.successful").value(true));
    }

    @Test
    public void testUpdatePurchaseStatus() throws Exception {
        UpdatePurchaseMemberRequest request = UpdatePurchaseMemberRequest.builder()
            .purchaseStatus(null)
            .build();

        mockMvc = MockMvcBuilders.standaloneSetup(purchaseMemberController).build();

        mockMvc.perform(put("/members/purchases/{purchaseId}", 1L)
                .header("Member-Id", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.header.successful").value(true));
    }

    @Test
    public void testDeletePurchases() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(purchaseMemberController).build();

        mockMvc.perform(delete("/members/purchases/{purchaseId}", 1L)
                .header("Member-Id", 1L))
            .andExpect(status().isNoContent());
    }
}
