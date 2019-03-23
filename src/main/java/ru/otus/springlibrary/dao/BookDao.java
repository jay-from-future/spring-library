package ru.otus.springlibrary.dao;

import ru.otus.springlibrary.domain.Book;

/**
 * DAO implementation for {@link Book} entity.
 */
public interface BookDao {

    /**
     * Inserts {@link Book} entity into database.
     *
     * @param book {@link Book} entity that should be inserted
     * @return {@link Book} entity filled with auto-generated id from database
     */
    Book insert(Book book);

    /**
     * Finds {@link Book} entity by id.
     *
     * @param id unique id in database
     * @return {@link Book} entity
     */
    Book findById(int id);

    /**
     * Updates {@link Book} entity in database
     *
     * @param book {@link Book} entity
     * @return updated {@link Book} entity from the database
     */
    Book update(Book book);

    /**
     * Deletes {@link Book} entity from database
     *
     * @param id id of {@link Book} entity that should be deleted
     * @return true if entity has been successfully deleted, otherwise - false
     */
    boolean delete(int id);
}
