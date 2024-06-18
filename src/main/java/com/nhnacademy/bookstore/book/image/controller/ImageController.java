package com.nhnacademy.bookstore.book.image.controller;

import com.amazonaws.services.s3.model.S3Object;
import com.nhnacademy.bookstore.book.image.exception.NotFindImageException;
import com.nhnacademy.bookstore.book.image.imageService.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    /**
     * front (toast ui)-> this -> nhn cloud (object storage)
     * front 에서 보여주는 이미지를 서버에 저장
     * @param image 받아올 이미지
     * @param type 파일을 저장할 위치 -> ex) book, review
     * @return 저장할 파일명 (새로운 UUID.확장자)
     */
    @PostMapping("/image/{type}/upload")
    public String uploadFile(@RequestParam MultipartFile image, @PathVariable String type) {

        String orgFilename = image.getOriginalFilename();                                                                   // 원본 파일명
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");                                   // 32자리 랜덤 문자열
        String extension = Objects.requireNonNull(orgFilename).substring(orgFilename.lastIndexOf(".") + 1);  // 확장자
        String saveFilename = uuid + "." + extension;                                                          // 디스크에 저장할 파일명
        imageService.uploadImage(image, type + "/" + saveFilename);

        return saveFilename;

    }


    /**
     * front (fileName) -> this -> nhn cloud (object storage) -> this -> front (image)
     * front 에서 요청한 이미지를 서버에서 받아서 보내기
     * @param fileName 보여줄 파일의 이름
     * @param type 파일을 보여줄 위치 -> ex) book, review
     * @return 서버에서 가져온 파일
     */
    @GetMapping("/image/{type}/download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam("fileName") String fileName, @PathVariable String type)  {
        byte[] content;
        try (S3Object s3Object = imageService.downloadImage(type + "/" + fileName)) {

            content = s3Object.getObjectContent().readAllBytes();
        } catch (IOException e) {
            throw new NotFindImageException();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName);

        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }



}
