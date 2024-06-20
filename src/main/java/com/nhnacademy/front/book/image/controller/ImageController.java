package com.nhnacademy.front.book.image.controller;

import com.nhnacademy.front.book.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author 한민기
 *
 */
@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    /**
     *  위지윅 에디터에서 파일을 바로바로 업로드하기 위해 사용
     * @param file 이미지 파일
     * @param type 이미지의 타입
     * @return 저장한 파일의 이름
     */
    @PostMapping("/{type}/upload")
    public String uploadImage(@RequestParam MultipartFile file, @PathVariable String type) {
        return imageService.upload(file, type);
    }

    /**
     *  위지윅 에디터에서 파일을 불러올때 사용
     * @param fileName 이미지 파일의 이름
     * @param type 이미지의 타입
     * @return 불러온 이미지
     */
    @GetMapping("/{type}/download")
    public ResponseEntity<byte[]> downloadImage(@RequestParam("fileName") String fileName, @PathVariable String type){
        return imageService.download(fileName, type);
    }
}
