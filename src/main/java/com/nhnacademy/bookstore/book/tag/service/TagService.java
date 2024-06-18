package com.nhnacademy.bookstore.book.tag.service;

import com.nhnacademy.bookstore.book.tag.dto.request.CreateTagRequest;
import com.nhnacademy.bookstore.book.tag.dto.request.DeleteTagRequest;
import com.nhnacademy.bookstore.book.tag.dto.request.UpdateTagRequest;
import com.nhnacademy.bookstore.entity.tag.Tag;

public interface TagService {

    public void addTag(CreateTagRequest tag);
    public void deleteTag(DeleteTagRequest tag);
    public void updateTag(UpdateTagRequest tag);
}
