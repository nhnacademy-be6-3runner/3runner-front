package com.nhnacademy.bookstore.book.image.imageService.Impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import com.nhnacademy.bookstore.book.image.exception.FailUploadImageException;
import com.nhnacademy.bookstore.book.image.exception.NotFindImageException;
import com.nhnacademy.bookstore.book.image.imageService.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

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
     * @param image     저장할 파일
     * @param storagePlace 저장할 파일의 위치/타입 -> book, review, test
     */
    @Override
    public String createImage(MultipartFile image, String storagePlace) {
        String orgFilename = image.getOriginalFilename();

        String fileName = fileNameMade(orgFilename, storagePlace);

        try {
            // MultipartFile을 바이트 배열로 변환
            byte[] bytes = image.getBytes();
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

        return fileName;
    }

    /**
     *  파일 이름으로 파일 조회
     * @param fileName -> 조회할 파일 위치 + 파일 이름
     * @return 조회할 파일을 S3Object 형식으로 받아옴
     */
    @Override
    public S3Object readImage(String fileName) throws NotFindImageException {
        return amazonS3.getObject(bucketName, fileName);

    }


    /**
     *  UUID 를 사용해서 파일 이름을 선정
     * @param orgFilename -> 파일의 원래 이름
     * @param storagePlace -> 저장하는 위치
     * @return -> 새로운 파일이름 + 저장하는 위치
     */
    private String fileNameMade(String orgFilename, String storagePlace) {

        TimeBasedGenerator timeBasedGenerator = Generators.timeBasedGenerator();
        String uuid = timeBasedGenerator.generate().toString().replaceAll("-", "");                       // time 을 사용한 UUID
        String extension = Objects.requireNonNull(orgFilename).substring(orgFilename.lastIndexOf(".") + 1);  // 확장자
        return storagePlace + "/" + uuid + "." + extension;
    }

}
