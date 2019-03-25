package ru.otus.springlibrary.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.otus.springlibrary.exception.AuthorNotFoundException;
import ru.otus.springlibrary.exception.CannotInsertException;
import ru.otus.springlibrary.exception.CannotUpdateException;
import ru.otus.springlibrary.domain.Author;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorDaoImpl implements AuthorDao {

    private static final String SELECT_ALL_FROM_AUTHOR = "select * from author";

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
        Author authorById;
        try {
            authorById = findById(id);
        } catch (AuthorNotFoundException e) {
            throw new CannotInsertException(String.format("Cannot retrieve author with id = %d after insert", id));
        }
        return authorById;
    }

    @Override
    public Author findById(int id) throws AuthorNotFoundException {
        Optional<Author> author = genericDao.findById(id, SELECT_FROM_AUTHOR_BY_ID, authorRowMapper);
        return author.orElseThrow(AuthorNotFoundException::new);
    }

    @Override
    public Author update(Author author) {
        int id = author.getId();
        int updatedRow = genericDao.update(author, UPDATE_AUTHOR_BY_ID);
        if (updatedRow == 0) {
            throw new CannotUpdateException("Cannot update: " + id);
        }
        Author authorById;
        try {
            authorById = findById(id);
        } catch (AuthorNotFoundException e) {
            throw new CannotInsertException(String.format("Cannot retrieve author with id = %d after update", id));
        }
        return authorById;
    }

    @Override
    public boolean delete(int id) {
        return genericDao.delete(id, DELETE_FROM_AUTHOR_BY_ID);
    }

    @Override
    public List<Author> getAllAuthors() {
        return genericDao.getAll(SELECT_ALL_FROM_AUTHOR, authorRowMapper);
    }
}
