package ru.otus.springlibrary.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Genre {

    private long id;

    private String genre;

    public Genre(String genre) {
        this.genre = genre;
    }

    public Genre(long id, String genre) {
        this.id = id;
        this.genre = genre;
    }

    public Genre(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return genre;
    }
}
