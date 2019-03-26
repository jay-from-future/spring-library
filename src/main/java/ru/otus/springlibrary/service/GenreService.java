package ru.otus.springlibrary.service;

import ru.otus.springlibrary.domain.Genre;

import java.util.List;

/**
 * Service to manage Genres in spring-library.
 */
public interface GenreService {

    /**
     * Returns all genres from the database
     *
     * @return list of genres
     */
    List<Genre> getAllGenres();

    /**
     * Adds new genre into library
     *
     * @param genre genre
     * @return true - if genre has been added, false - if genre already exists
     */
    boolean addGenre(String genre);

    /**
     * Deletes genre with such id
     *
     * @param id genre id
     * @return true - if genre has been deleted, false - if genre does not exist
     */
    boolean delete(long id);

}
