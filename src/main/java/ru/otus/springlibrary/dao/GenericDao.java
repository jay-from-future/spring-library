package ru.otus.springlibrary.dao;

/**
 * Generic DAO.
 */
public interface GenericDao<T> {

    /**
     * Inserts entity into database.
     *
     * @param t entity that should be inserted
     */
    void insert(T t);

    /**
     * Finds entity by id.
     *
     * @param tClass entity class
     * @param id     unique id in database
     * @return entity, if entity with such id exists
     */
    T findById(Class<T> tClass, long id);

    /**
     * Updates entity in database
     *
     * @param t entity
     */
    void update(T t);

    /**
     * Deletes entity from database
     *
     * @param t entity that should be deleted
     */
    void delete(T t);
}
