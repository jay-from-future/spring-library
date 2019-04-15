package ru.otus.springlibrary.exception;

public class GenreNotFoundException extends RuntimeException {

    public GenreNotFoundException() {
    }

    public GenreNotFoundException(String message) {
        super(message);
    }
}
