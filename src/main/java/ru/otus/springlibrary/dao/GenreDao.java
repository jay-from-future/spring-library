package ru.otus.springlibrary.dao;

import ru.otus.springlibrary.domain.Genre;
import ru.otus.springlibrary.exception.GenreNotFoundException;

import java.util.List;

/**
 * DAO implementation for {@link Genre} entity.
 */
public interface GenreDao {

    /**
     * Inserts {@link Genre} entity into database.
     *
     * @param genre {@link Genre} entity that should be inserted
     */
    void insert(Genre genre);

    /**
     * Finds {@link Genre} entity by id.
     *
     * @param id unique id in database
     * @return {@link Genre} entity
     * @throws GenreNotFoundException if genre with such id was not found
     */
    Genre findById(long id) throws GenreNotFoundException;

    /**
     * Updates {@link Genre} entity in database
     *
     * @param genre {@link Genre} entity
     */
    void update(Genre genre);

    /**
     * Deletes {@link Genre} entity from database
     *
     * @param genre {@link Genre} entity that should be deleted
     */
    void delete(Genre genre);

    /**
     * Returns all {@link Genre} entities from database.
     *
     * @return list of genres
     */
    List<Genre> getAllGenres();
}
