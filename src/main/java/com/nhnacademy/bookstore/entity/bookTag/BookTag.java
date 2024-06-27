package com.nhnacademy.bookstore.entity.bookTag;

import com.nhnacademy.bookstore.entity.book.Book;
import com.nhnacademy.bookstore.entity.tag.Tag;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BookTag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	private Book book;

	@ManyToOne
	private Tag tag;

	public BookTag(Book book, Tag tag) {
		this.book = book;
		this.tag = tag;
	}
}
