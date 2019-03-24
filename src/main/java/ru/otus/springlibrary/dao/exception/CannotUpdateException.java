package ru.otus.springlibrary.dao.exception;

public class CannotUpdateException extends RuntimeException {

    public CannotUpdateException(String message) {
        super(message);
    }
}
