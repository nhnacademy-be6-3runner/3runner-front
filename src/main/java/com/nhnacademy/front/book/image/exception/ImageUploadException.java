package com.nhnacademy.front.book.image.exception;

/**
 * @author 한민기
 */
public class ImageUploadException extends RuntimeException {

    /**
     * ImageClient 에서 오류가 발생시 예외 처리
     */
    public ImageUploadException() {
        super("이미지 업로드 실패");
    }
}
