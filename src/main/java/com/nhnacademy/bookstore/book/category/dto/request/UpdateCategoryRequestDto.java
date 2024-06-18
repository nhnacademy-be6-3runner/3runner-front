package com.nhnacademy.bookstore.book.category.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCategoryRequestDto {
    private String name;
    @Setter
    private long parentId;
}
