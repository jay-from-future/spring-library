package ru.otus.springlibrary.dao;

import ru.otus.springlibrary.domain.Review;
import ru.otus.springlibrary.exception.ReviewNotFoundException;

/**
 * DAO implementation for {@link Review} entity.
 */
public interface ReviewDao {

    /**
     * Inserts {@link Review} entity into database.
     *
     * @param review {@link Review} entity that should be inserted
     */
    void insert(Review review);

    /**
     * Finds {@link Review} entity by id.
     *
     * @param id unique id in database
     * @return {@link Review} entity
     * @throws ReviewNotFoundException if genre with such id was not found
     */
    Review findById(long id) throws ReviewNotFoundException;

    /**
     * Updates {@link Review} entity in database
     *
     * @param review {@link Review} entity
     */
    void update(Review review);

    /**
     * Deletes {@link Review} entity from database
     *
     * @param review {@link Review} entity that should be deleted
     */
    void delete(Review review);
}
