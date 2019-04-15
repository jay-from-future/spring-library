package ru.otus.springlibrary.dao;

import ru.otus.springlibrary.domain.Book;
import ru.otus.springlibrary.exception.BookNotFoundException;

import java.util.List;

/**
 * DAO implementation for {@link Book} entity.
 */
public interface BookDao {

    /**
     * Inserts {@link Book} entity into database.
     *
     * @param book {@link Book} entity that should be inserted
     */
    void insert(Book book);

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
     */
    void update(Book book);

    /**
     * Deletes {@link Book} entity from database
     *
     * @param book {@link Book} entity that should be deleted
     */
    void delete(Book book);

    /**
     * Returns all {@link Book} entities from database.
     *
     * @return list of books
     */
    List<Book> getAllBooks();
}
