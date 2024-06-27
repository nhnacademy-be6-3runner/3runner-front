package com.nhnacademy.bookstore.entity.bookImage;

import com.nhnacademy.bookstore.entity.book.Book;
import com.nhnacademy.bookstore.entity.bookImage.enums.BookImageType;
import com.nhnacademy.bookstore.entity.totalImage.TotalImage;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull
	@Size(min = 1, max = 50)
	@Column(unique = true)
	private String url;

	@NotNull
	private BookImageType type;

	@ManyToOne(cascade = CascadeType.ALL)
	private Book book;

	@OneToOne(cascade = CascadeType.ALL)
	private TotalImage totalImage;

	public BookImage(String url, BookImageType type, Book book) {
		this.url = url;
		this.type = type;
		this.book = book;
	}
}
