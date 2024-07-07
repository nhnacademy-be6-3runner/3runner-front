package com.nhnacademy.bookstore.purchase.purchaseBook.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nhnacademy.bookstore.purchase.purchaseBook.dto.request.CreatePurchaseBookRequest;
import com.nhnacademy.bookstore.purchase.purchaseBook.dto.request.UpdatePurchaseBookRequest;
import com.nhnacademy.bookstore.purchase.purchaseBook.dto.response.ReadPurchaseBookResponse;

/**
 * 주문-책 interface
 *
 * @author 정주혁
 */
public interface PurchaseBookService {
    void deletePurchaseBook(long purchaseBookId);

    Long createPurchaseBook(CreatePurchaseBookRequest createPurchaseBookRequest);

    Long updatePurchaseBook(UpdatePurchaseBookRequest updatePurchaseBookRequest);

    Page<ReadPurchaseBookResponse> readBookByPurchaseResponses(Long purchaseId, Pageable pageable);

    Page<ReadPurchaseBookResponse> readGuestBookByPurchaseResponses(String purchaseId, Pageable pageable);

}
