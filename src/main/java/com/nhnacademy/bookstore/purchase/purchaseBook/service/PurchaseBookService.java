package com.nhnacademy.bookstore.purchase.purchaseBook.service;

import com.nhnacademy.bookstore.purchase.purchaseBook.dto.request.CreatePurchaseBookRequest;
import com.nhnacademy.bookstore.purchase.purchaseBook.dto.request.DeletePurchaseBookRequest;
import com.nhnacademy.bookstore.purchase.purchaseBook.dto.request.ReadPurchaseIdRequest;
import com.nhnacademy.bookstore.purchase.purchaseBook.dto.request.UpdatePurchaseBookRequest;
import com.nhnacademy.bookstore.purchase.purchaseBook.dto.response.ReadPurchaseBookResponse;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
