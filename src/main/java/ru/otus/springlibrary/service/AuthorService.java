package ru.otus.springlibrary.service;

import ru.otus.springlibrary.domain.Author;

/**
 * Service to manage Authors in spring-library.
 */
public interface AuthorService {

    /**
     * Returns all authors from the database
     *
     * @return authors
     */
    Iterable<Author> findAll();

    /**
     * Adds new author
     *
     * @param firstName author's first name
     * @param lastName  author's last name
     * @return added author
     */
    Author addAuthor(String firstName, String lastName);

    /**
     * Deletes author with such id
     *
     * @param id author id
     */
    void delete(long id);
}
