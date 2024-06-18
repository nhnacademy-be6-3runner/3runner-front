package com.nhnacademy.bookstore.book.image.imageService;

import com.amazonaws.services.s3.model.S3Object;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    public String uploadImage(MultipartFile file, String fileName);
    public S3Object downloadImage(String fileName);
}
