package com.nhnacademy.bookstore.purchase.purchaseBook.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nhnacademy.bookstore.purchase.purchaseBook.dto.response.ReadPurchaseBookResponse;

public interface PurchaseBookCustomRepository {
	Page<ReadPurchaseBookResponse> readBookPurchaseResponses(Long purchaseId, Pageable pageable);

	Page<ReadPurchaseBookResponse> readGuestBookPurchaseResponses(String purchaseId, Pageable pageable);

}
