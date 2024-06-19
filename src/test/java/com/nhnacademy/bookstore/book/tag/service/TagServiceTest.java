package com.nhnacademy.bookstore.book.tag.service;

import com.nhnacademy.bookstore.book.tag.dto.request.CreateTagRequest;
import com.nhnacademy.bookstore.book.tag.dto.request.DeleteTagRequest;
import com.nhnacademy.bookstore.book.tag.dto.request.UpdateTagRequest;
import com.nhnacademy.bookstore.book.tag.repository.TagRepository;
import com.nhnacademy.bookstore.entity.tag.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagServiceImpl tagService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTag_Success() {
        CreateTagRequest createTagRequest = CreateTagRequest.builder()
                .name("New Tag")
                .build();

        when(tagRepository.findByName(createTagRequest.name())).thenReturn(Optional.empty());
        when(tagRepository.save(any(Tag.class))).thenReturn(new Tag());

        tagService.createTag(createTagRequest);

        verify(tagRepository, times(1)).save(any(Tag.class));
    }

    @Test
    public void testDeleteTag_Success() {
        DeleteTagRequest deleteTagRequest = DeleteTagRequest.builder()
                .tagId(1L)
                .build();

        when(tagRepository.existsById(deleteTagRequest.tagId())).thenReturn(true);
        doNothing().when(tagRepository).deleteById(deleteTagRequest.tagId());

        tagService.deleteTag(deleteTagRequest);

        verify(tagRepository, times(1)).deleteById(deleteTagRequest.tagId());
    }

    @Test
    public void testUpdateTag_Success() {
        UpdateTagRequest updateTagRequest = UpdateTagRequest.builder()
                .tagId(1L)
                .tagName("Updated Tag Name")
                .build();

        Tag existingTag = new Tag();
        existingTag.setName("Old Tag Name");

        when(tagRepository.findById(updateTagRequest.tagId())).thenReturn(Optional.of(existingTag));
        when(tagRepository.save(any(Tag.class))).thenReturn(existingTag);

        tagService.updateTag(updateTagRequest);

        verify(tagRepository, times(1)).save(existingTag);
    }
}
