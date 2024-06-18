package com.nhnacademy.bookstore.book.bookImage.service.Impl;

import com.nhnacademy.bookstore.book.bookImage.repository.BookImageRepository;
import com.nhnacademy.bookstore.book.bookImage.service.BookImageService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookImageServiceImpl implements BookImageService {

    private final BookImageRepository bookImageRepository;


}
