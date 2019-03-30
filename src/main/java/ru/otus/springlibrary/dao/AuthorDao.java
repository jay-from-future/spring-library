package ru.otus.springlibrary.dao;

import ru.otus.springlibrary.domain.Author;
import ru.otus.springlibrary.exception.AuthorNotFoundException;

import java.util.List;

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
     * @throws AuthorNotFoundException if author with such id was not found
     */
    Author findById(long id) throws AuthorNotFoundException;

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
