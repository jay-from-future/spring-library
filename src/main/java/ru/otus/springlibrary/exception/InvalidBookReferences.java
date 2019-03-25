package ru.otus.springlibrary.exception;

public class InvalidBookReferences extends RuntimeException {

    public InvalidBookReferences(String message, Throwable cause) {
        super(message, cause);
    }
}
