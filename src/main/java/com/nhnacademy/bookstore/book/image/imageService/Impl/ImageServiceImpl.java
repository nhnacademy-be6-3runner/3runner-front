package com.nhnacademy.bookstore.book.image.imageService.Impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.nhnacademy.bookstore.book.image.exception.FailUploadImageException;
import com.nhnacademy.bookstore.book.image.exception.NotFindImageException;
import com.nhnacademy.bookstore.book.image.imageService.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * @author 한민기
 *
 * bucketName 은 nhn object storage => 컨테이너 이름
 */
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    /**
     *  this -> object storage (bucket)
     * @param file     저장할 파일
     * @param fileName 저장할 파일의 위치 + 저장할 파일의 이름
     */
    @Override
    public void uploadImage(MultipartFile file, String fileName) {
        try {
            // MultipartFile을 바이트 배열로 변환
            byte[] bytes = file.getBytes();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

            // 메타데이터 설정 (필요한 경우)
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(bytes.length);

            // S3에 파일 업로드
            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, byteArrayInputStream, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

        } catch (IOException e) {
            throw new FailUploadImageException();
        }
    }

    /**
     *  파일 이름으로 파일 조회
     * @param fileName -> 조회할 파일 위치 + 파일 이름
     * @return 조회할 파일을 S3Object 형식으로 받아옴
     */
    @Override
    public S3Object downloadImage(String fileName) throws NotFindImageException {
        return amazonS3.getObject(bucketName, fileName);

    }
}
