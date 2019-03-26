package ru.otus.springlibrary.dao;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.springlibrary.exception.CannotInsertException;
import ru.otus.springlibrary.service.GenreServiceImpl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GenericDaoImpl<T> implements GenericDao<T> {

    private static final Logger logger = LoggerFactory.getLogger(GenreServiceImpl.class);

    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public int insert(T t, String insertSql) {
        BeanPropertySqlParameterSource paramSource = new BeanPropertySqlParameterSource(t);
        return insert(t, insertSql, paramSource);
    }

    @Override
    public int insert(T t, String insertSql, Map<String, Object> parameters) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource(parameters);
        return insert(t, insertSql, paramSource);
    }

    @Override
    public Optional<T> findById(long id, String findByIdSql, RowMapper<T> entityMapper) {
        MapSqlParameterSource map = new MapSqlParameterSource("id", id);
        T item = null;
        try {
            item = jdbcOperations.queryForObject(findByIdSql, map, entityMapper);
        } catch (IncorrectResultSizeDataAccessException e) {
            logger.debug(e.getMessage());
        }
        return Optional.ofNullable(item);
    }

    @Override
    public int update(T t, String updateSql) {
        return jdbcOperations.update(updateSql, new BeanPropertySqlParameterSource(t));
    }


    @Override
    public boolean delete(long id, String deleteByIdSql) {
        MapSqlParameterSource map = new MapSqlParameterSource("id", id);
        return jdbcOperations.update(deleteByIdSql, map) == 1;
    }

    @Override
    public List<T> getAll(String selectAllSql, RowMapper<T> entityMapper) {
        return jdbcOperations.query(selectAllSql, entityMapper);
    }

    private int insert(T t, String insertSql, SqlParameterSource paramSource) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int update = jdbcOperations.update(insertSql, paramSource, keyHolder);
        Number key = keyHolder.getKey();
        if (update != 1 || key == null) {
            throw new CannotInsertException("Cannot insert: " + t);
        }
        return key.intValue();
    }
}
