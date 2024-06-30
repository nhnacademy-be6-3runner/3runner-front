package com.nhnacademy.bookstore.elastic.document.book;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Document(indexName = "3runner_book")
@AllArgsConstructor
@NotBlank
@Getter
@Setter
public class BookDocument {
	@Id
	@Field(type = FieldType.Keyword)
	private long id;

	@Field(type = FieldType.Text)
	private String title;

	@Field(type = FieldType.Text)
	private String author;

	@Field(type = FieldType.Text)
	private String thumbnail;

	@Field(type = FieldType.Text)
	private String publisher;

	@Field(type = FieldType.Date, format = DateFormat.date_optional_time)
	private String publishedDate;

	@Field(type = FieldType.Keyword)
	private List<String> tagList;

	@Field(type = FieldType.Keyword)
	private List<String> categoryList;
}