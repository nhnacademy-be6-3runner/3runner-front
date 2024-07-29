package com.nhnacademy.front.book.image.service.Impl;

import com.nhnacademy.front.book.image.controller.feign.ImageClient;
import com.nhnacademy.front.book.image.exception.ImageDownloadException;
import com.nhnacademy.front.book.image.exception.ImageUploadException;
import com.nhnacademy.front.book.image.service.ImageService;
import com.nhnacademy.front.util.ApiResponse;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 한민기
 *
 */
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

	private final ImageClient imageClient;

	/**
	 *  이미지 클라이언트에서 받아온 정보를 가지고 헤더 확인해 오류이면 예외처리
	 *  오류가 아니면 body 값을  보내줌
	 * @param file 이미지 파일
	 * @param type 파일의 타입
	 * @return 이미지 파일의 이름(변경된)
	 */
	@Override
	public String upload(MultipartFile file, String type) {

		ApiResponse<String> response = imageClient.uploadImage(file, type);

		if (!response.getHeader().isSuccessful()) {
			throw new ImageUploadException();
		}

		return response.getBody().getData();
	}

	/**
	 *  이미지 클라이언트에서 받아온 정보를 가지고 헤더 확인해 오류이면 예외처리
	 *  오류가 아니면 body 값을 보내줌
	 * @param fileName 파일 이름
	 * @param type 파일 타입
	 * @return 이미지 파일
	 */
	@Override
	public ResponseEntity<byte[]> download(String fileName, String type) {
		ResponseEntity<byte[]> response = imageClient.downloadFile(fileName, type);

		if (Objects.isNull(response)) {
			throw new ImageDownloadException();
		}

		return response;
	}
}
