package ru.otus.springlibrary.dao.exception;

public class CannotInsertException extends RuntimeException {

    public CannotInsertException(String message) {
        super(message);
    }
}
