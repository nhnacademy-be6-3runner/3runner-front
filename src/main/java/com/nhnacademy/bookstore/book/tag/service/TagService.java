package com.nhnacademy.bookstore.book.tag.service;

import com.nhnacademy.bookstore.book.tag.dto.request.CreateTagRequest;
import com.nhnacademy.bookstore.book.tag.dto.request.DeleteTagRequest;
import com.nhnacademy.bookstore.book.tag.dto.request.UpdateTagRequest;
import com.nhnacademy.bookstore.entity.tag.Tag;

/**
 * Tag CRUD 서비스
 * @author 정주혁
 */
public interface TagService {

    public Long createTag(CreateTagRequest tag);
    public void deleteTag(DeleteTagRequest tag);
    public Long updateTag(UpdateTagRequest tag);
}
