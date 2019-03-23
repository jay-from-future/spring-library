package ru.otus.springlibrary.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.springlibrary.domain.Author;

import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AuthorDaoImpl implements AuthorDao {

    private static final String UPDATE_AUTHOR_BY_ID = "update author set first_name = :first_name, " +
            "last_name = :last_name where id =:id";

    private static final String SQL_DELETE_FROM_AUTHOR_BY_ID = "delete from author where id = :id";

    private static final String SQL_INSERT_INTO_AUTHOR = "insert into author (first_name, last_name) " +
            "values (:firstName, :lastName)";

    private static final String SELECT_FROM_AUTHOR_BY_ID = "select * from author where id = :id";

    private final NamedParameterJdbcOperations jdbcOperations;

    private RowMapper<Author> authorRowMapper = (rs, rowNum) ->
            new Author(rs.getInt("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name")
            );

    @Override
    public Author insert(Author author) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int update = jdbcOperations.update(SQL_INSERT_INTO_AUTHOR, new BeanPropertySqlParameterSource(author), keyHolder);
        Number key = keyHolder.getKey();
        if (update != 1 || key == null) {
            throw new CannotInsertException("Cannot insert: " + author);
        }
        return findById(key.intValue());
    }

    @Override
    public Author findById(int id) {
        MapSqlParameterSource map = new MapSqlParameterSource("id", id);
        return jdbcOperations.queryForObject(SELECT_FROM_AUTHOR_BY_ID, map, authorRowMapper);
    }

    @Override
    public Author update(Author author) {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("id", author.getId());
        parameterMap.put("first_name", author.getFirstName());
        parameterMap.put("last_name", author.getLastName());
        jdbcOperations.update(UPDATE_AUTHOR_BY_ID, parameterMap);
        return findById(author.getId());
        /*
        todo why this code produces following error:
        org.springframework.dao.InvalidDataAccessApiUsageException:No value supplied for the SQL parameter 'first_name':
        Invalid property 'first_name' of bean class [ru.otus.springlibrary.domain.Author]:Bean property 'first_name'
        is not readable or has an invalid getter method:
        Does the return type of the getter match the parameter type of the setter?

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int update = jdbcOperations.update(UPDATE_AUTHOR_BY_ID, new BeanPropertySqlParameterSource(author), keyHolder);
        Number key = keyHolder.getKey();
        if (update != 1 || key == null) {
            throw new CannotUpdateException("Cannot update: " + author);
        }
        return findById(key.intValue());
        */
    }

    @Override
    public boolean delete(int id) {
        MapSqlParameterSource map = new MapSqlParameterSource("id", id);
        return jdbcOperations.update(SQL_DELETE_FROM_AUTHOR_BY_ID, map) == 1;
    }
}
