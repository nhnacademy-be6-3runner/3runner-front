//package com.nhnacademy.bookstore.purchase.bookCart.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.nhnacademy.bookstore.purchase.bookCart.dto.request.CreateBookCartRequest;
//import com.nhnacademy.bookstore.purchase.bookCart.dto.request.DeleteBookCartRequest;
//import com.nhnacademy.bookstore.purchase.bookCart.dto.request.ReadAllBookCartMemberRequest;
//import com.nhnacademy.bookstore.purchase.bookCart.dto.request.UpdateBookCartRequest;
//import com.nhnacademy.bookstore.purchase.bookCart.dto.response.ReadAllBookCartMemberResponse;
//import com.nhnacademy.bookstore.purchase.bookCart.service.BookCartMemberService;
//
//import java.util.List;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Collections;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(BookCartMemberController.class)
//public class BookCartMemberControllerTest {
//
//	@Autowired
//	private MockMvc mockMvc;
//
//	@MockBean
//	private BookCartMemberService bookCartMemberService;
//
//	@Autowired
//	private ObjectMapper objectMapper;
//
//	private CreateBookCartRequest createBookCartMemberRequest;
//	private UpdateBookCartRequest updateBookCartMemberRequest;
//	private DeleteBookCartRequest deleteBookCartMemberRequest;
//	private ReadAllBookCartMemberRequest readAllBookCartMemberRequest;
//	private ReadAllBookCartMemberResponse readAllBookCartMemberResponse;
//
//	@BeforeEach
//	void setUp() {
//		createBookCartMemberRequest = CreateBookCartRequest.builder()
//			.quantity(1)
//			.bookId(1L)
//			.userId(1L)
//			.build();
//
//		updateBookCartMemberRequest = UpdateBookCartRequest.builder()
//			.bookCartId(1L)
//			.quantity(2)
//			.bookId(1)
//			.cartId(1)
//			.build();
//
//		deleteBookCartMemberRequest = DeleteBookCartRequest.builder()
//			.userId(1L)
//			.build();
//
//		readAllBookCartMemberRequest = ReadAllBookCartMemberRequest.builder()
//			.userId(1L)
//			.build();
//
//		readAllBookCartMemberResponse = ReadAllBookCartMemberResponse.builder()
//			.quantity(1)
//			.book(null)
//			.build();
//	}
//
//	@Test
//	void testCreateBookCartMember() throws Exception {
//		when(bookCartMemberService.createBookCartMember(any(CreateBookCartRequest.class)))
//			.thenReturn(1L);
//
//		mockMvc.perform(post("/bookstore/cart")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(objectMapper.writeValueAsString(createBookCartMemberRequest)))
//			.andExpect(content().contentType("application/json"))
//			.andExpect(jsonPath("$.header.resultCode").value(201))
//			.andExpect(jsonPath("$.header.successful").value(true))
//			.andExpect(jsonPath("$.body.data").value(1L));
//	}
//
//	@Test
//	void testReadAllBookCartMember() throws Exception {
//		List<ReadAllBookCartMemberResponse> page = Collections.singletonList(readAllBookCartMemberResponse);
//		Pageable pageable = PageRequest.of(0, 10);
//
//		when(bookCartMemberService.readAllCartMember(any(ReadAllBookCartMemberRequest.class)))
//			.thenReturn(page);
//
//		mockMvc.perform(get("/bookstore/cart")
//				.header("Member-Id",1L)
//				.param("page", "0")
//				.param("size", "10")
//				.param("sort", ""))
//			.andExpect(status().isOk())
//			.andExpect(jsonPath("$.body.data.content[0].quantity").value(1));
//	}
//
//	@Test
//	void testUpdateBookCartMember() throws Exception {
//		when(bookCartMemberService.updateBookCartMember(any(UpdateBookCartRequest.class)))
//			.thenReturn(1L);
//
//		mockMvc.perform(put("/bookstore/cart")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(objectMapper.writeValueAsString(updateBookCartMemberRequest)))
//			.andExpect(status().isOk())
//			.andExpect(jsonPath("$.body.data").value(1L));
//	}
//
//	@Test
//	void testDeleteBookCartMember() throws Exception {
//		Mockito.doNothing().when(bookCartMemberService).deleteBookCartMember(any(DeleteBookCartRequest.class));
//
//		mockMvc.perform(delete("/bookstore/cart")
//				.header("Member-Id", "1"))
//			.andExpect(content().contentType("application/json"))
//			.andExpect(jsonPath("$.header.resultCode").value(204))
//			.andExpect(jsonPath("$.header.successful").value(true))
//			.andExpect(jsonPath("$.body.data").isEmpty());
//	}
//}
