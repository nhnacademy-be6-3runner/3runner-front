package com.nhnacademy.bookstore.book.category.dto.request;


import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCategoryRequestDto {
    private String name;
    @Setter
    private Long parentId;
}
