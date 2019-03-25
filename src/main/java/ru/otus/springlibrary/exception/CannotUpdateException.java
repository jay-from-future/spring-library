package ru.otus.springlibrary.exception;

public class CannotUpdateException extends RuntimeException {

    public CannotUpdateException(String message) {
        super(message);
    }
}
