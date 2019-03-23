package ru.otus.springlibrary.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Genre {

    private int id;

    private String genre;

    public Genre(String genre) {
        this.genre = genre;
    }

    public Genre(int id, String genre) {
        this.id = id;
        this.genre = genre;
    }
}
