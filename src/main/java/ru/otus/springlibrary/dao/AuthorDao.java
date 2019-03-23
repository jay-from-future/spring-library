package ru.otus.springlibrary.dao;

import ru.otus.springlibrary.domain.Author;

/**
 * DAO implementation for {@link Author} entity.
 */
public interface AuthorDao {

    /**
     * Inserts {@link Author} entity into database.
     *
     * @param author {@link Author} entity that should be inserted
     * @return {@link Author} entity filled with auto-generated id from database
     */
    Author insert(Author author);

    /**
     * Finds {@link Author} entity by id.
     *
     * @param id unique id in database
     * @return {@link Author} entity
     */
    Author findById(int id);

    /**
     * Updates {@link Author} entity in database
     *
     * @param author {@link Author} entity
     * @return updated {@link Author} entity from the database
     */
    Author update(Author author);

    /**
     * Deletes {@link Author} entity from database
     *
     * @param id id of {@link Author} entity that should be deleted
     * @return true if entity has been successfully deleted, otherwise - false
     */
    boolean delete(int id);
}
