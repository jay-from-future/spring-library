package ru.otus.springlibrary.dao;

import ru.otus.springlibrary.exception.GenreNotFoundException;
import ru.otus.springlibrary.domain.Genre;

import java.util.List;

/**
 * DAO implementation for {@link Genre} entity.
 */
public interface GenreDao {

    /**
     * Inserts {@link Genre} entity into database.
     *
     * @param genre {@link Genre} entity that should be inserted
     * @return {@link Genre} entity filled with auto-generated id from database
     */
    Genre insert(Genre genre);

    /**
     * Finds {@link Genre} entity by id.
     *
     * @param id unique id in database
     * @return {@link Genre} entity
     * @throws GenreNotFoundException if genre with such id was not found
     */
    Genre findById(int id) throws GenreNotFoundException;

    /**
     * Updates {@link Genre} entity in database
     *
     * @param genre {@link Genre} entity
     * @return updated {@link Genre} entity from the database
     */
    Genre update(Genre genre);

    /**
     * Deletes {@link Genre} entity from database
     *
     * @param id id of {@link Genre} entity that should be deleted
     * @return true if entity has been successfully deleted, otherwise - false
     */
    boolean delete(int id);

    /**
     * Returns all {@link Genre} entities from database.
     *
     * @return list of genres
     */
    List<Genre> getAllGenres();
}
