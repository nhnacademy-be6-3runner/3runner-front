package com.nhnacademy.bookstore.book.category.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// TODO record 형으로 수정
public class UpdateCategoryRequestDto {
    private String name;
    @Setter
    private Long parentId;
}
