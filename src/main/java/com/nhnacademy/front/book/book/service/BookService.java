package com.nhnacademy.front.book.book.service;

import com.nhnacademy.front.book.book.dto.request.UserCreateBookRequest;

public interface BookService {
    void saveBook(UserCreateBookRequest createBookRequest, String imageName);
    void saveApiBook(String isbn);
}
