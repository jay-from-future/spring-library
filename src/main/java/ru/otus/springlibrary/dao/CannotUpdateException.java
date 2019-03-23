package ru.otus.springlibrary.dao;

class CannotUpdateException extends RuntimeException {

    CannotUpdateException(String message) {
        super(message);
    }
}
