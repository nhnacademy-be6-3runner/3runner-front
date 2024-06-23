package com.nhnacademy.bookstore.book.bookImage.service;


import com.nhnacademy.bookstore.book.bookImage.dto.request.CreateBookImageRequest;
import com.nhnacademy.bookstore.entity.bookImage.BookImage;
import java.util.List;

public interface BookImageService {

    void createBookImage(List<CreateBookImageRequest> bookImageRequestList);
    void createBookImage(CreateBookImageRequest bookImage);
}
