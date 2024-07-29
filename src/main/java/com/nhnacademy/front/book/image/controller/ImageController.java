package com.nhnacademy.front.book.image.controller;

import com.nhnacademy.front.book.image.service.ImageService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 김은비, 한민기
 *
 */
@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {
	private final ImageService imageService;

	/**
	 * 위지윅 에디터에서 파일을 바로바로 업로드하기 위해 사용.
	 *
	 * @param image 이미지 파일
	 * @param type 이미지의 타입
	 * @return 저장한 파일의 이름
	 */
	@PostMapping("/{type}/upload")
	public String uploadImage(@RequestParam MultipartFile image, @PathVariable String type) {
		return imageService.upload(image, type);
	}

	/**
	 * 위지윅 에디터에서 파일을 불러올때 사용.
	 *
	 * @param fileName 이미지 파일의 이름
	 * @param type 이미지의 타입
	 * @return 불러온 이미지
	 */
	@GetMapping("/{type}/download")
	public ResponseEntity<byte[]> downloadImage(@RequestParam("fileName") String fileName, @PathVariable String type)
		throws IOException {
		try {
			return imageService.download(fileName, type);
		} catch (Exception e) {
			ClassPathResource defaultImg = new ClassPathResource("default-book.png");
			return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_PNG)
				.body(defaultImg.getContentAsByteArray());
		}
	}
}