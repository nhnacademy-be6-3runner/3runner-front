package com.nhnacademy.bookstore.book.tag.controller;

import com.nhnacademy.bookstore.book.bookTag.exception.ReadBookTagRequestFormException;
import com.nhnacademy.bookstore.book.tag.dto.request.CreateTagRequest;
import com.nhnacademy.bookstore.book.tag.dto.request.DeleteTagRequest;
import com.nhnacademy.bookstore.book.tag.dto.request.UpdateTagRequest;
import com.nhnacademy.bookstore.book.tag.exception.CreateTagRequestFormException;
import com.nhnacademy.bookstore.book.tag.exception.DeleteTagRequestFormException;
import com.nhnacademy.bookstore.book.tag.exception.UpdateTagRequestFormException;
import com.nhnacademy.bookstore.book.tag.service.TagService;
import com.nhnacademy.bookstore.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
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
    public ApiResponse<Void> createTag(@Valid CreateTagRequest createTagRequest,
                                    BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new CreateTagRequestFormException(bindingResult.getFieldErrors().toString());
        }
        tagService.createTag(createTagRequest);

        return new ApiResponse<>(new ApiResponse.Header(true, HttpStatus.CREATED.value(), "SUCCESS_CREATE"), new ApiResponse.Body<>(null));
    }

    /**
     * Tag 제거
     *
     * @param deleteTagRequest 제거할 태그의 id
     * @return ApiResponse<Void> 성공시 success헤더만 보냄
     */
    @DeleteMapping
    public ApiResponse<Void> deleteTag(@Valid DeleteTagRequest deleteTagRequest,
                                       BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {

            throw new DeleteTagRequestFormException(bindingResult.getFieldErrors().toString());
        }
        tagService.deleteTag(deleteTagRequest);
        return new ApiResponse<>(new ApiResponse.Header(true, HttpStatus.NO_CONTENT.value(), "SUCCESS_DELETE"), new ApiResponse.Body<>(null));


    }

    /**
     * Tag 업데이트
     *
     * @param updateTagRequest 업데이트 할 테그의 id 및 이름
     * @return 성공시 success헤더만 보냄
     */
    @PutMapping
    public ApiResponse<Void> updateTag(@Valid UpdateTagRequest updateTagRequest,
                                       BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {

            throw new UpdateTagRequestFormException(bindingResult.getFieldErrors().toString());
        }
        tagService.updateTag(updateTagRequest);
        return ApiResponse.success(null);
    }

}
