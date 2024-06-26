package com.nhnacademy.front.book.image.service;


import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 한민기
 */
public interface ImageService {

    /**
     *  이미지 파일 업로드 -> imageClient 로 보내주기
     * @param file 이미지 파일
     * @param type 파일의 타입
     * @return 이미지의 파일의 이름
     */
    String upload(MultipartFile file, String type);

    ResponseEntity<byte[]> download(String fileName, String type);
}
