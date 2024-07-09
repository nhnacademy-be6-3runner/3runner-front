package com.nhnacademy.bookstore.elastic.document.book;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(indexName = "3runner_book_20240703")
@Setting(settingPath = "/elastic/book-document-settings.json")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDocument {
	@Id
	@Field(type = FieldType.Keyword)
	private long id;

	@Field(type = FieldType.Text, copyTo = {"keywordText"}, analyzer = "nori_analyzer")
	// @Field(type = FieldType.Text, copyTo = {"keywordText"})
	private String title;

	@Field(type = FieldType.Keyword, copyTo = {"keywordList", "keywordText"})
	private String author;

	@Field(type = FieldType.Keyword)
	private String thumbnail;

	@Field(type = FieldType.Keyword, copyTo = {"keywordList", "keywordText"})
	private String publisher;

	@Field(type = FieldType.Date, format = DateFormat.date_optional_time, copyTo = {"keywordText"})
	private String publishedDate;

	@Field(type = FieldType.Text, copyTo = {"keywordList", "keywordText"}, analyzer = "nori_analyzer")
	private List<String> tagList;

	@Field(type = FieldType.Keyword, copyTo = {"keywordList", "keywordText"})
	private List<String> categoryList;

	@Field(type = FieldType.Text, analyzer = "nori_analyzer")
	// @Field(type = FieldType.Text)
	private List<String> keywordText;

	@Field(type = FieldType.Text)
	private List<String> keywordList;

	public BookDocument(long id, String title, String author, String thumbnail, String publisher, String publishedDate,
		List<String> tagList, List<String> categoryList) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.thumbnail = thumbnail;
		this.publisher = publisher;
		this.publishedDate = publishedDate;
		this.tagList = tagList;
		this.categoryList = categoryList;
	}
}