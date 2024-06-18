package com.nhnacademy.bookstore.book.tag.controller;

import com.nhnacademy.bookstore.book.tag.dto.request.CreateTagRequest;
import com.nhnacademy.bookstore.book.tag.dto.request.DeleteTagRequest;
import com.nhnacademy.bookstore.book.tag.dto.request.UpdateTagRequest;
import com.nhnacademy.bookstore.book.tag.service.TagService;
import com.nhnacademy.bookstore.entity.tag.Tag;
import com.nhnacademy.bookstore.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 태그들을 만들고 제거하고 수정하는 컨트롤러
 *
 * @author 정주혁
 */
@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    /**
     * Tag 저장
     *
     * @param createTagRequest 생성할 태그의 이름
     * @return ApiResponse<Void> 성공시 success헤더만 보냄
     */
    @PostMapping
    public ApiResponse<Void> addTag(@RequestBody CreateTagRequest createTagRequest) {
        tagService.addTag(createTagRequest);

        return ApiResponse.success(null);
    }

    /**
     * Tag 제거
     *
     * @param deleteTagRequest 제거할 태그의 id
     * @return ApiResponse<Void> 성공시 success헤더만 보냄
     */
    @DeleteMapping
    public ApiResponse<Void> deleteTag(@RequestParam DeleteTagRequest deleteTagRequest) {
        tagService.deleteTag(deleteTagRequest);
        return ApiResponse.success(null);

    }

    /**
     * Tag 업데이트
     *
     * @param updateTagRequest 업데이트 할 테그의 id 및 이름
     * @return 성공시 success헤더만 보냄
     */
    @PutMapping
    public ApiResponse<Void> updateTag(@RequestParam UpdateTagRequest updateTagRequest) {
        tagService.updateTag(updateTagRequest);
        return ApiResponse.success(null);
    }

}
