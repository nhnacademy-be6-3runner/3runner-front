package com.nhnacademy.bookstore.entity.category;

import com.nhnacademy.bookstore.entity.bookCategory.BookCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(min = 1, max = 30)
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;


    //연결

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL)
    private Set<Category> children = new HashSet<>();

    @Setter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BookCategory> bookCategorySet = new HashSet<>();

}
