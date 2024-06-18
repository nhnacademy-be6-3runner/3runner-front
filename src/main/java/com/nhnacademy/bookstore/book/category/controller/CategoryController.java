package com.nhnacademy.bookstore.book.category.controller;

import com.nhnacademy.bookstore.book.book.exception.CreateBookRequestFormException;
import com.nhnacademy.bookstore.book.category.dto.request.CreateCategoryRequestDto;
import com.nhnacademy.bookstore.book.category.service.CategoryService;
import com.nhnacademy.bookstore.entity.book.dto.CreateBookRequest;
import com.nhnacademy.bookstore.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ApiResponse<Void> createCategory(@Valid CreateCategoryRequestDto dto, BindingResult bindingResult) {

    }


//    @PostMapping("/book")
//    public ApiResponse<Void> createBook(@Valid CreateBookRequest createBookRequest, BindingResult bindingResult){
//        if(bindingResult.hasErrors()){
//            StringBuilder errorMessage = new StringBuilder();
//            bindingResult.getFieldErrors().forEach(error ->
//                    errorMessage.append(error.getField())
//                            .append(": ")
//                            .append(error.getDefaultMessage())
//                            .append("\n")
//            );
//            throw new CreateBookRequestFormException(errorMessage.toString());
//        }
}
