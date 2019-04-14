package ru.otus.springlibrary.dao;

import ru.otus.springlibrary.domain.Author;

import java.util.List;
import java.util.Optional;

/**
 * DAO implementation for {@link Author} entity.
 */
public interface AuthorDao {

    /**
     * Inserts {@link Author} entity into database.
     *
     * @param author {@link Author} entity that should be inserted
     */
    void insert(Author author);

    /**
     * Finds {@link Author} entity by id.
     *
     * @param id unique id in database
     * @return {@link Author} entity
     */
    Author findById(long id);

    /**
     * Updates {@link Author} entity in database
     *
     * @param author {@link Author} entity
     */
    void update(Author author);

    /**
     * Deletes {@link Author} entity from database
     *
     * @param author {@link Author} entity that should be deleted
     */
    void delete(Author author);

    /**
     * Returns all {@link Author} entities from database.
     *
     * @return list of authors
     */
    List<Author> getAllAuthors();
}
