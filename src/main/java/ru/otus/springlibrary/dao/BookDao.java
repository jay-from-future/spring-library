package ru.otus.springlibrary.dao;

import ru.otus.springlibrary.exception.BookNotFoundException;
import ru.otus.springlibrary.domain.Book;

import java.util.List;

/**
 * DAO implementation for {@link Book} entity.
 */
public interface BookDao {

    /**
     * Inserts {@link Book} entity into database.
     *
     * @param book     {@link Book} entity that should be inserted
     * @return {@link Book} entity filled with auto-generated id from database
     */
    Book insert(Book book);

    /**
     * Finds {@link Book} entity by id.
     *
     * @param id unique id in database
     * @return {@link Book} entity
     * @throws BookNotFoundException if book with such id was not found
     */
    Book findById(long id) throws BookNotFoundException;

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
    boolean delete(long id);

    /**
     * Returns all {@link Book} entities from database.
     *
     * @return list of books
     */
    List<Book> getAllBooks();
}
