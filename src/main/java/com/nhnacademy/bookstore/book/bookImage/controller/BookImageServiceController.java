package com.nhnacademy.bookstore.book.bookImage.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.nhnacademy.bookstore.book.bookImage.service.BookImageService;
import com.nhnacademy.bookstore.book.image.imageService.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 *
 * @author : 한민기
 *
 */
@RestController
@RequiredArgsConstructor
public class BookImageServiceController {

    private final BookImageService bookImageService;
    private final ImageService imageService;
    /**
     * front (toast ui)-> this -> nhn cloud (object storage)
     * front 에서 보여주는 이미지를 서버에 저장
     * @param image 받아올 이미지
     * @param filename 파일의 변경된 이름
     * @return 실행 확인 값
     */
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile image, @RequestParam String filename) {
        String fileUrl = imageService.uploadImage(image, "book/" + filename);
        return ResponseEntity.ok(fileUrl);
    }

    /**
     * front (fileName) -> this -> nhn cloud (object storage) -> this -> front (image)
     * front 에서 요청한 이미지를 서버에서 받아서 보내기
     * @param fileName 보여줄 파일의 이름
     * @return 서버에서 가져온 파일
     * @throws IOException -> 서버에서 받아온 파일을 byte 형식으로 변경시 오류가 발생 할 수 있음
     */

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam("fileName") String fileName) throws IOException {
        S3Object s3Object = imageService.downloadImage("book/"+fileName);

        byte[] content = s3Object.getObjectContent().readAllBytes();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName);

        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }

}
