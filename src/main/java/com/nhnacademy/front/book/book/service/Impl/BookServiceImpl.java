package com.nhnacademy.front.book.book.service.Impl;

import com.nhnacademy.front.book.book.controller.feign.BookClient;
import com.nhnacademy.front.book.book.dto.request.CreateBookRequest;
import com.nhnacademy.front.book.book.dto.request.UserCreateBookRequest;
import com.nhnacademy.front.book.book.service.BookService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookClient bookClient;


    /**
     * 입력 받은 항목을 Api 서버에게 보내주기 위한 형태로 변형
     *  그후 보내기
     * @param userCreateBookRequest 입력 받은 항목들
     */
    @Override
    public void saveBook(UserCreateBookRequest userCreateBookRequest, String imageName) {


        CreateBookRequest createBookRequest = CreateBookRequest.builder()
                .title(userCreateBookRequest.title())
                .description(userCreateBookRequest.description())
                .publishedDate(stringToZonedDateTime(userCreateBookRequest.publishedDate()))
                .price(userCreateBookRequest.price())
                .quantity(userCreateBookRequest.quantity())
                .sellingPrice(userCreateBookRequest.sellingPrice())
                .packing(userCreateBookRequest.packing())
                .author(userCreateBookRequest.author())
                .isbn(userCreateBookRequest.isbn())
                .publisher(userCreateBookRequest.publisher())
                .imageName(imageName)
                .imageList(descriptionToImageList(userCreateBookRequest.description()))
                .tagIds(stringIdToList(userCreateBookRequest.tagList()))
                //TODO 수정 필요
                .categoryIds(List.of(1L, 2L))
//                .categoryIds(stringIdToList(userCreateBookRequest.categoryList()))
                .build();

        log.info("Create book : {}", createBookRequest);

        bookClient.createBook(createBookRequest);
    }

    //TODO 아직 미구현
    /**
     *
     *  내용 에서 이미지 추출하는 코드
     * @param description
     * @return
     */
    private List<String> descriptionToImageList(String description) {
        List<String> imageList = new ArrayList<>();
        String[] split = description.split("fileName=");
        if(split.length > 1) {
            for(int i = 1; i < split.length; i++) {
                imageList.add(split[i].substring(0, split[i].indexOf(")") ));
            }
        }
        log.info("imageList : {}", imageList);
        return imageList;
    }

    /**
     * String 으로 되어있는 아이디 값들을 리스트로 변환
     * @param StringId id 가 하나의 String 으로 이어져있음 ex -> 1,2,3,4
     * @return 리스트로 반환
     */

    private List<Long> stringIdToList(String StringId) {
        List<Long> idList = new ArrayList<>();
        if(Objects.isNull(StringId)) {
            return idList;
        }
        String[]  idSplit = StringId.split(",");

        for (String idStr : idSplit) {
            idList.add(Long.parseLong(idStr));
        }
        log.info("list {}", idList);
        return idList;
    }

    /**
     * String -> ZoneDateTime 으로 변경
     * @param dateStr 바꿀 date String  형태는 yyyy-MM-dd
     * @return ZoneDateTime 의 날짜
     */
    private ZonedDateTime stringToZonedDateTime(String dateStr) {
        if(Objects.isNull(dateStr)) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDateStr = LocalDate.parse(dateStr, formatter);

        return localDateStr.atStartOfDay(ZoneId.systemDefault());
    }
}