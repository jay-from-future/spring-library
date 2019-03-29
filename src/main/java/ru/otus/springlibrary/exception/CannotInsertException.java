package ru.otus.springlibrary.exception;

public class CannotInsertException extends RuntimeException {

    public CannotInsertException(String message) {
        super(message);
    }
}
