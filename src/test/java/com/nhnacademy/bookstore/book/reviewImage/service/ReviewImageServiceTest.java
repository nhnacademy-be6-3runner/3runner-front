package com.nhnacademy.bookstore.book.reviewImage.service;

import com.nhnacademy.bookstore.book.review.repository.ReviewRepository;
import com.nhnacademy.bookstore.book.reviewImage.repository.ReviewImageRepository;
import com.nhnacademy.bookstore.book.reviewImage.service.impl.ReviewImageServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class ReviewImageServiceTest {

    @Mock
    private ReviewImageRepository reviewImageRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewImageServiceImpl reviewImageService;
}
