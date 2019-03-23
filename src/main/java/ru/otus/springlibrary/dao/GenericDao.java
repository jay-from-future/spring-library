package ru.otus.springlibrary.dao;

import org.springframework.jdbc.core.RowMapper;

/**
 * Generic DAO.
 */
public interface GenericDao<T> {

    /**
     * Inserts entity into database.
     *
     * @param t entity that should be inserted
     * @return entity filled with auto-generated id from database
     */
    int insert(T t, String insertSql);

    /**
     * Finds entity by id.
     *
     * @param id unique id in database
     * @return entity
     */
    T findById(int id, String findByIdSql, RowMapper<T> entityMapper);

    /**
     * Updates entity in database
     *
     * @param t entity
     * @return updated entity from the database
     */
    int update(T t, String updateSql);

    /**
     * Deletes entity from database
     *
     * @param id id of entity that should be deleted
     * @return true if entity has been successfully deleted, otherwise - false
     */
    boolean delete(int id, String deleteByIdSql);
}
