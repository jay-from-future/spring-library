package ru.otus.springlibrary.service;

import ru.otus.springlibrary.domain.Book;

import java.util.List;

/**
 * Service to manage Books in spring-library.
 */
public interface BookService {

    /**
     * Returns all books from the database
     *
     * @return list of books
     */
    List<Book> getAllBooks();
}
