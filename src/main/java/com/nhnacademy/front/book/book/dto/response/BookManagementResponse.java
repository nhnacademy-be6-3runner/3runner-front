package com.nhnacademy.front.book.book.dto.response;

public record BookManagementResponse(
	long id, String title, int price, int sellingPrice, String author, int quantity, int viewCount
) {
}
