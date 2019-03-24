package ru.otus.springlibrary.service;

import ru.otus.springlibrary.domain.Author;

import java.util.List;

/**
 * Service to manage Authors in spring-library.
 */
public interface AuthorService {

    /**
     * Returns all authors from the database
     *
     * @return list of authors
     */
    List<Author> getAllAuthors();
}
