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
}
