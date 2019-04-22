package ru.otus.springlibrary.service;

import org.bson.types.ObjectId;
import ru.otus.springlibrary.domain.Genre;

/**
 * Service to manage Genres in spring-library.
 */
public interface GenreService {

    /**
     * Returns all genres from the database
     *
     * @return genres
     */
    Iterable<Genre> findAll();

    /**
     * Adds new genre into library
     *
     * @param genre genre
     * @return added genre
     */
    Genre addGenre(String genre);

    /**
     * Deletes genre with such id
     *
     * @param id genre id
     */
    void delete(ObjectId id);

}
