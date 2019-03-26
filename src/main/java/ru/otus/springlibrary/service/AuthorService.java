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

    /**
     * Adds new author
     *
     * @param firstName author's first name
     * @param lastName  author's last name
     * @return true - if author has been added, false - if author already exists
     */
    boolean addAuthor(String firstName, String lastName);

    /**
     * Deletes author with such id
     *
     * @param id author id
     * @return true - if author has been deleted, false - if author does not exist
     */
    boolean delete(long id);
}
