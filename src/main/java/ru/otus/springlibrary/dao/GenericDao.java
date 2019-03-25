package ru.otus.springlibrary.dao;

import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Generic DAO.
 */
public interface GenericDao<T> {

    /**
     * Inserts entity into database.
     *
     * @param t         entity that should be inserted
     * @param insertSql SQL insert statement
     * @return entity filled with auto-generated id from database
     */
    int insert(T t, String insertSql);

    /**
     * Inserts entity into database.
     *
     * @param t          entity that should be inserted
     * @param insertSql  SQL insert statement
     * @param parameters special parameter mapping (if standard not applicable)
     * @return entity filled with auto-generated id from database
     */
    int insert(T t, String insertSql, Map<String, Object> parameters);

    /**
     * Finds entity by id.
     *
     * @param id           unique id in database
     * @param findByIdSql  SQL find by id statement
     * @param entityMapper mapper to convert between database row and entity
     * @return entity, if entity with such id exists
     */
    Optional<T> findById(int id, String findByIdSql, RowMapper<T> entityMapper);

    /**
     * Updates entity in database
     *
     * @param t         entity
     * @param updateSql SQL update statement
     * @return updated entity from the database
     */
    int update(T t, String updateSql);

    /**
     * Deletes entity from database
     *
     * @param id            id of entity that should be deleted
     * @param deleteByIdSql SQL delete by id statement
     * @return true if entity has been successfully deleted, otherwise - false
     */
    boolean delete(int id, String deleteByIdSql);

    /**
     * Returns all entities of this type from database
     * Warning: it is not optimized to retrieve data from the database piece by piece!
     *
     * @param selectAllSql SQL select all statement
     * @param entityMapper mapper to convert between database row and entity
     * @return list of all entities
     */
    List<T> getAll(String selectAllSql, RowMapper<T> entityMapper);
}
