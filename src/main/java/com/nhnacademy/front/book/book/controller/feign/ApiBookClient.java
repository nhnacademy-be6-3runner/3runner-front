package com.nhnacademy.front.book.book.controller.feign;

import com.nhnacademy.front.util.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * ISBN 으로 북 추가할 때 사용할 feign client.
 *
 * @author 한민기
 */
@FeignClient(name = "ApiBookClient", url = "${feign.client.url}/bookstore/api/books")
public interface ApiBookClient {
	/**
	 * isbn 으로 책 추가.
	 * @param isbnId 책의 isbn
	 * @return 성공 코드
	 */
	@GetMapping("/{isbnId}")
	ApiResponse<Void> createApiBook(@PathVariable String isbnId);

}
