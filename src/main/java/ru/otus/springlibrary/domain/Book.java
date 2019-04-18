package ru.otus.springlibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "book")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    private ObjectId id;

    @Field(value = "title")
    private String title;

//    @DBRef(lazy = true)
    @DBRef
    private List<Author> authors;

    @DBRef
    private List<Genre> genres;

    @Field(value = "reviews")
    private List<String> reviews;

    public Book(String title, List<Author> authors, List<Genre> genres) {
        this.title = title;
        this.authors = authors;
        this.genres = genres;
    }

    public Book(String title) {
        this.title = title;
    }
}
