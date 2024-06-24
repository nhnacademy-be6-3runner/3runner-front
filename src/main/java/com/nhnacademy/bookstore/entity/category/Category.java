package com.nhnacademy.bookstore.entity.category;

import com.nhnacademy.bookstore.entity.bookCategory.BookCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    // 연결
    @Setter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Category> children = new ArrayList<>();

    @Setter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<BookCategory> bookCategoryList = new ArrayList<>();

    public void setParent(Category parent) {
        this.parent = parent;
    }
    public void addChildren(Category child) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        child.setParent(this);
        this.children.add(child);
    }
}
