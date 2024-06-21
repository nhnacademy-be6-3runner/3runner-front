package com.nhnacademy.bookstore.purchase.purchaseBook.service.impl;

import com.nhnacademy.bookstore.entity.purchaseBook.PurchaseBook;
import com.nhnacademy.bookstore.purchase.purchaseBook.dto.request.CreatePurchaseBookRequest;
import com.nhnacademy.bookstore.purchase.purchaseBook.exception.NotExistsPurchaseBook;
import com.nhnacademy.bookstore.purchase.purchaseBook.dto.request.ReadPurchaseIdRequest;
import com.nhnacademy.bookstore.purchase.purchaseBook.dto.request.UpdatePurchaseBookRequest;
import com.nhnacademy.bookstore.purchase.purchaseBook.dto.response.ReadBookByPurchase;
import com.nhnacademy.bookstore.purchase.purchaseBook.dto.response.ReadPurchaseBookResponse;
import com.nhnacademy.bookstore.purchase.purchaseBook.repository.PurchaseBookRepository;
import com.nhnacademy.bookstore.purchase.purchaseBook.service.purchaseBookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class purchaseBookServiceImpl implements purchaseBookService {
    private final PurchaseBookRepository purchaseBookRepository;

    public purchaseBookServiceImpl(PurchaseBookRepository purchaseBookRepository) {
        this.purchaseBookRepository = purchaseBookRepository;
    }

    public List<ReadPurchaseBookResponse> readBookByPurchaseResponses(ReadPurchaseIdRequest readPurchaseIdRequest){
        List<PurchaseBook> purchaseBooks = purchaseBookRepository.findAllByPurchaseId(readPurchaseIdRequest.purchaseId());
        return purchaseBooks.stream().map(purchaseBook->ReadPurchaseBookResponse.builder().
                readBookByPurchase(ReadBookByPurchase.builder()
                        .title(purchaseBook.getBook().getTitle())
                        .description(purchaseBook.getBook().getDescription())
                        .bookCategories(purchaseBook.getBook().getBookCategorySet())
                        .bookImages(purchaseBook.getBook().getBookImageSet())
                        .sellingPrice(purchaseBook.getBook().getSellingPrice())
                        .packing(purchaseBook.getBook().isPacking())
                        .bookTags(purchaseBook.getBook().getBookTagSet())
                        .publisher(purchaseBook.getBook().getPublisher())
                        .price(purchaseBook.getBook().getPrice())
                        .build())
                .quantity(purchaseBook.getQuantity())
                .price(purchaseBook.getPrice())
                .build()).collect(Collectors.toList());
    }
    public Long updatePurchaseBook(UpdatePurchaseBookRequest updatePurchaseBookRequest){
        PurchaseBook purchaseBook = purchaseBookRepository.findByPurchaseIdAndBookId(updatePurchaseBookRequest.purchaseId(),updatePurchaseBookRequest.bookId()).orElse(null);
        if(purchaseBook == null){
            throw new NotExistsPurchaseBook();
        }
        return purchaseBook.getId();
    }
    public Long createPurchaseBook(CreatePurchaseBookRequest createPurchaseBookRequest){


    }

}
