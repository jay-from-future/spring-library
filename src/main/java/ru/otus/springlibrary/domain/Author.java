package ru.otus.springlibrary.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Author {

    private long id;

    private String firstName;

    private String lastName;

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Author(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Author(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
