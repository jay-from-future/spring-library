package ru.otus.springlibrary.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GenericDaoImpl<T> implements GenericDao<T> {

    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public int insert(T t, String insertSql) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int update = jdbcOperations.update(insertSql, new BeanPropertySqlParameterSource(t), keyHolder);
        Number key = keyHolder.getKey();
        if (update != 1 || key == null) {
            throw new CannotInsertException("Cannot insert: " + t);
        }
        return key.intValue();
    }

    @Override
    public T findById(int id, String findByIdSql, RowMapper<T> entityMapper) {
        MapSqlParameterSource map = new MapSqlParameterSource("id", id);
        return jdbcOperations.queryForObject(findByIdSql, map, entityMapper);
    }

    @Override
    public int update(T t, String updateSql) {
        return jdbcOperations.update(updateSql, new BeanPropertySqlParameterSource(t));
    }


    @Override
    public boolean delete(int id, String deleteByIdSql) {
        MapSqlParameterSource map = new MapSqlParameterSource("id", id);
        return jdbcOperations.update(deleteByIdSql, map) == 1;
    }
}
