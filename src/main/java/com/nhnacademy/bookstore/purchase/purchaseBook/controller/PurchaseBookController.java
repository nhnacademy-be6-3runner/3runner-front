package com.nhnacademy.bookstore.purchase.purchaseBook.controller;

import com.nhnacademy.bookstore.purchase.purchaseBook.dto.request.CreatePurchaseBookRequest;
import com.nhnacademy.bookstore.purchase.purchaseBook.dto.request.DeletePurchaseBookRequest;
import com.nhnacademy.bookstore.purchase.purchaseBook.dto.request.ReadPurchaseIdRequest;
import com.nhnacademy.bookstore.purchase.purchaseBook.dto.request.UpdatePurchaseBookRequest;
import com.nhnacademy.bookstore.purchase.purchaseBook.dto.response.ReadPurchaseBookResponse;
import com.nhnacademy.bookstore.purchase.purchaseBook.exception.CreatePurchaseBookRequestFormException;
import com.nhnacademy.bookstore.purchase.purchaseBook.exception.DeletePurchaseBookRequestFormException;
import com.nhnacademy.bookstore.purchase.purchaseBook.exception.ReadPurchaseBookRequestFormException;
import com.nhnacademy.bookstore.purchase.purchaseBook.service.PurchaseBookService;
import com.nhnacademy.bookstore.purchase.purchaseBook.service.impl.PurchaseBookServiceImpl;
import com.nhnacademy.bookstore.util.ApiResponse;
import com.nhnacademy.bookstore.util.ValidationUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 주문내역 - 책 controller
 *
 * @author 정주혁
 */
@RestController
@RequestMapping("/purchase/book")
public class PurchaseBookController {

    PurchaseBookService purchaseBookService;

    /**
     * 주문 id로 자신이 주문한 책들 조회
     *
     * @param readPurchaseIdRequest 주문아이디가 포함된 requestDto
     * @param bindingResult requestDto의 오류발생시 오류 처리를 위한 파라미터
     * @return 주문id로 조회된 책들 리스트 반환
     */
    @GetMapping
    public ApiResponse<List<ReadPurchaseBookResponse>> readPurchaseBook(@RequestBody @Valid ReadPurchaseIdRequest readPurchaseIdRequest,BindingResult bindingResult) {
        ValidationUtils.validateBindingResult(bindingResult,new ReadPurchaseBookRequestFormException());
        return ApiResponse.success(purchaseBookService.readBookByPurchaseResponses(readPurchaseIdRequest));
    }

    /**
     * 주문 책 생성
     *
     * @param createPurchaseBookRequest 주문 책에 필요한 request dto
     * @param bindingResult requestDto의 오류발생시 오류 처리를 위한 파라미터
     * @return 생성한 주문 책 id
     */
    @PostMapping
    public ApiResponse<Long> createPurchaseBook(@RequestBody @Valid CreatePurchaseBookRequest createPurchaseBookRequest,BindingResult bindingResult) {
        ValidationUtils.validateBindingResult(bindingResult,new CreatePurchaseBookRequestFormException());
        return ApiResponse.createSuccess(purchaseBookService.createPurchaseBook(createPurchaseBookRequest));
    }

    /**
     * 주문 책 삭제
     *
     * @param deletePurchaseBookRequest 삭제할 주문 책의 id request dto
     * @param bindingResult requestDto의 오류발생시 오류 처리를 위한 파라미터
     * @return 삭제후 void return
     */
    @DeleteMapping
    public ApiResponse<Void> deletePurchaseBook(@RequestBody @Valid DeletePurchaseBookRequest deletePurchaseBookRequest, BindingResult bindingResult) {
        ValidationUtils.validateBindingResult(bindingResult,new DeletePurchaseBookRequestFormException());
        purchaseBookService.deletePurchaseBook(deletePurchaseBookRequest);
        return ApiResponse.deleteSuccess(null);
    }


    /**
     *
     *
     * @param updatePurchaseBookRequest
     * @param bindingResult
     * @return
     */
    @PutMapping
    public ApiResponse<Long> updatePurchaseBook(@RequestBody @Valid UpdatePurchaseBookRequest updatePurchaseBookRequest, BindingResult bindingResult) {
        ValidationUtils.validateBindingResult(bindingResult,new RuntimeException());
        return ApiResponse.success(purchaseBookService.updatePurchaseBook(updatePurchaseBookRequest));
    }
}
