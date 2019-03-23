package ru.otus.springlibrary.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.otus.springlibrary.domain.Author;

@Repository
@RequiredArgsConstructor
public class AuthorDaoImpl implements AuthorDao {

    private static final String INSERT_INTO_AUTHOR = "insert into author (firstname, lastname) " +
            "values (:firstName, :lastName)";

    private static final String SELECT_FROM_AUTHOR_BY_ID = "select * from author where id = :id";

    private static final String UPDATE_AUTHOR_BY_ID = "update author set firstname = :firstName, " +
            "lastname = :lastName where id =:id";

    private static final String DELETE_FROM_AUTHOR_BY_ID = "delete from author where id = :id";

    private final GenericDao<Author> genericDao;

    private RowMapper<Author> authorRowMapper = (rs, rowNum) ->
            new Author(rs.getInt("id"),
                    rs.getString("firstname"),
                    rs.getString("lastname")
            );

    @Override
    public Author insert(Author author) {
        int id = genericDao.insert(author, INSERT_INTO_AUTHOR);
        return findById(id);
    }

    @Override
    public Author findById(int id) {
        return genericDao.findById(id, SELECT_FROM_AUTHOR_BY_ID, authorRowMapper);
    }

    @Override
    public Author update(Author author) {
        int updatedRow = genericDao.update(author, UPDATE_AUTHOR_BY_ID);
        if (updatedRow == 0) {
            throw new CannotUpdateException("Cannot update: " + author.getId());
        }
        return findById(author.getId());
    }

    @Override
    public boolean delete(int id) {
        return genericDao.delete(id, DELETE_FROM_AUTHOR_BY_ID);
    }
}
