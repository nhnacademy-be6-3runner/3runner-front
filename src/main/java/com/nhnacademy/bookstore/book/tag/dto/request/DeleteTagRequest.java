package com.nhnacademy.bookstore.book.tag.dto.request;

import lombok.Builder;

@Builder
public record DeleteTagRequest(long tagId) {

}
