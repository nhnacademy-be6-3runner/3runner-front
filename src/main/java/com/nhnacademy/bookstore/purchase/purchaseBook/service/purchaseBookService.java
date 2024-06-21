package com.nhnacademy.bookstore.purchase.purchaseBook.service;

import com.nhnacademy.bookstore.purchase.purchaseBook.dto.request.CreatePurchaseBookRequest;
import com.nhnacademy.bookstore.purchase.purchaseBook.dto.request.DeletePurchaseBookRequest;
import com.nhnacademy.bookstore.purchase.purchaseBook.dto.request.ReadPurchaseIdRequest;
import com.nhnacademy.bookstore.purchase.purchaseBook.dto.request.UpdatePurchaseBookRequest;
import com.nhnacademy.bookstore.purchase.purchaseBook.dto.response.ReadPurchaseBookResponse;

import java.util.List;

public interface PurchaseBookService {
    void deletePurchaseBook(DeletePurchaseBookRequest purchaseBookRequest);
    Long createPurchaseBook(CreatePurchaseBookRequest createPurchaseBookRequest);
    Long updatePurchaseBook(UpdatePurchaseBookRequest updatePurchaseBookRequest);
    List<ReadPurchaseBookResponse> readBookByPurchaseResponses(ReadPurchaseIdRequest readPurchaseIdRequest);

}
