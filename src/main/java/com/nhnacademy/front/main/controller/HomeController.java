package com.nhnacademy.front.main.controller;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping
	public String home() {
		return "main/main";
	}
}

//
//private OrderSpecifier<?>[] getSort(Sort sort) {
//	return sort.stream()
//			.map(order -> switch (order.getProperty()) {
//				case "viewCount" -> new OrderSpecifier<>(
//						order.isAscending() ? Order.ASC : Order.DESC,
//						qBook.viewCount);
//				case "likes" -> new OrderSpecifier<>(
//						order.isAscending() ? Order.ASC : Order.DESC,
//						qBookLike.count());
//				case "publishedDate" -> new OrderSpecifier<>(
//						order.isAscending() ? Order.ASC : Order.DESC,
//						qBook.publishedDate);
//				case "price" -> new OrderSpecifier<>(
//						order.isAscending() ? Order.ASC : Order.DESC,
//						qBook.price);
//				default -> throw new IllegalArgumentException("정렬 기준이 잘못되었습니다: " + order.getProperty());
//			})
//			.toArray(OrderSpecifier[]::new);
//}