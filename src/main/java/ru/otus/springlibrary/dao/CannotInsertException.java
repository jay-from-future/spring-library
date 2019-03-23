package ru.otus.springlibrary.dao;

class CannotInsertException extends RuntimeException {

    CannotInsertException(String message) {
        super(message);
    }
}
