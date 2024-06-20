package com.nhnacademy.front.book.image.controller.feign;

import com.nhnacademy.front.util.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value="3runner-bookstore", path="/images")
public interface ImageClient {

    /**
     *  bookstore 와 이미지 업로드 통신
     * @param image 저장할 이미지
     * @param type 저장할 이미지의 타입 (확장자가 아님 예시 -> book, review, test)
     * @return 저장한 이미지의 파일 이름
     */
    @PostMapping("/{type}/upload")
    ApiResponse<String> uploadImage(@RequestBody MultipartFile image, @PathVariable String type);

    /**
     *  bookstore 에서 이미지 가져오기
     * @param fileName 가져올 이미지 파일 이름
     * @param type  가저올 이미지의 타입 (확장자가 아님 예시 -> book, review, test)
     * @return 이미지 파일
     */
    @GetMapping("/{type}/download")
    ApiResponse<ResponseEntity<byte[]>> downloadFile(@RequestParam("fileName") String fileName, @PathVariable String type);
}
