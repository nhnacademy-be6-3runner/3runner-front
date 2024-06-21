package com.nhnacademy.bookstore.purchase.purchaseBook.service.impl;

import com.nhnacademy.bookstore.book.book.repository.BookRepository;
import com.nhnacademy.bookstore.entity.purchaseBook.PurchaseBook;
import com.nhnacademy.bookstore.purchase.purchase.repository.PurchaseRepository;
import com.nhnacademy.bookstore.purchase.purchaseBook.dto.request.CreatePurchaseBookRequest;
import com.nhnacademy.bookstore.purchase.purchaseBook.dto.request.DeletePurchaseBookRequest;
import com.nhnacademy.bookstore.purchase.purchaseBook.exception.NotExistsPurchaseBook;
import com.nhnacademy.bookstore.purchase.purchaseBook.dto.request.ReadPurchaseIdRequest;
import com.nhnacademy.bookstore.purchase.purchaseBook.dto.request.UpdatePurchaseBookRequest;
import com.nhnacademy.bookstore.purchase.purchaseBook.dto.response.ReadBookByPurchase;
import com.nhnacademy.bookstore.purchase.purchaseBook.dto.response.ReadPurchaseBookResponse;
import com.nhnacademy.bookstore.purchase.purchaseBook.repository.PurchaseBookRepository;
import com.nhnacademy.bookstore.purchase.purchaseBook.service.PurchaseBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class PurchaseBookServiceImpl implements PurchaseBookService {
    private final PurchaseBookRepository purchaseBookRepository;
    private final PurchaseRepository purchaseRepository;
    private final BookRepository bookRepository;



    @Override
    public List<ReadPurchaseBookResponse> readBookByPurchaseResponses(ReadPurchaseIdRequest readPurchaseIdRequest){
        List<PurchaseBook> purchaseBooks = purchaseBookRepository.findAllByPurchaseId(readPurchaseIdRequest.purchaseId());
        return purchaseBooks.stream().map(purchaseBook->ReadPurchaseBookResponse.builder().
                readBookByPurchase(ReadBookByPurchase.builder()
                        .title(purchaseBook.getBook().getTitle())
                        .description(purchaseBook.getBook().getDescription())
                        .bookCategories(purchaseBook.getBook().getBookCategoryList())
                        .bookImages(purchaseBook.getBook().getBookImageList())
                        .sellingPrice(purchaseBook.getBook().getSellingPrice())
                        .packing(purchaseBook.getBook().isPacking())
                        .bookTags(purchaseBook.getBook().getBookTagList())
                        .publisher(purchaseBook.getBook().getPublisher())
                        .price(purchaseBook.getBook().getPrice())
                        .build())
                .quantity(purchaseBook.getQuantity())
                .price(purchaseBook.getPrice())
                .build()).collect(Collectors.toList());
    }
    @Override
    public Long updatePurchaseBook(UpdatePurchaseBookRequest updatePurchaseBookRequest){
        PurchaseBook purchaseBook = purchaseBookRepository.findByPurchaseIdAndBookId(updatePurchaseBookRequest.purchaseId(),updatePurchaseBookRequest.bookId()).orElse(null);
        if(purchaseBook == null){
            throw new NotExistsPurchaseBook();
        }
        return purchaseBook.getId();
    }
    @Override
    public Long createPurchaseBook(CreatePurchaseBookRequest createPurchaseBookRequest){
        PurchaseBook purchaseBook = new PurchaseBook(bookRepository.findById(createPurchaseBookRequest.bookId()).orElseThrow(()->new RuntimeException())
                ,createPurchaseBookRequest.quantity()
                ,createPurchaseBookRequest.price()
                ,purchaseRepository.findById(createPurchaseBookRequest.purchaseId()).orElseThrow(()->new RuntimeException()));

        return purchaseBookRepository.save(purchaseBook).getId();
    }
    @Override
    public void deletePurchaseBook(DeletePurchaseBookRequest purchaseBookRequest){
        purchaseBookRepository.deleteById(purchaseBookRequest.purchaseBookId());
    }

}
