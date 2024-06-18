package com.nhnacademy.bookstore.book.tag.service;

import com.nhnacademy.bookstore.book.tag.dto.request.CreateTagRequest;
import com.nhnacademy.bookstore.book.tag.dto.request.DeleteTagRequest;
import com.nhnacademy.bookstore.book.tag.dto.request.UpdateTagRequest;
import com.nhnacademy.bookstore.book.tag.repository.TagRepository;
import com.nhnacademy.bookstore.entity.tag.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * tag CRUD 서비스(read 제외)
 *
 * @author 정주혁
 */
@Service
@Transactional
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;

    /**
     * tag 추가
     *
     * @param tag 추가할 태그 이름
     *
     */
    @Override
    public void addTag(CreateTagRequest tag) {
        Tag tagEntity =new Tag();
        tagEntity.setName(tag.name());

    }

    /**
     * tag 제거
     *
     * @param tag 제거할 태그 id
     *
     */
    @Override
    public void deleteTag(DeleteTagRequest tag) {
        tagRepository.deleteById(tag.tagId());
    }

    /**
     * tag 수정
     *
     * @param tag 변경할 태그 내용
     */
    @Override
    public void updateTag(UpdateTagRequest tag) {
        Tag tagEntity = tagRepository.findById(tag.tagId()).orElse(null);
        if(tagEntity == null) {

            throw new NullPointerException();
        }
        tagEntity.setName(tag.tagName());
        tagRepository.save(tagEntity);
    }
}
