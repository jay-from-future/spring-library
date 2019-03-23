package ru.otus.springlibrary.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Book {

    private int id;

    private Author author;

    private Genre genre;
}
