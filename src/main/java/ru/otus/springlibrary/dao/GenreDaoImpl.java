package ru.otus.springlibrary.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.otus.springlibrary.exception.CannotInsertException;
import ru.otus.springlibrary.exception.GenreNotFoundException;
import ru.otus.springlibrary.domain.Genre;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GenreDaoImpl implements GenreDao {

    private static final String DELETE_FROM_GENRE_BY_ID = "delete from genre where id = :id";

    private static final String SELECT_FROM_GENRE_BY_ID = "select * from genre where id = :id";

    private static final String SELECT_ALL_FROM_GENRE = "select * from genre";

    private static final String INSERT_INTO_GENRE = "insert into genre (genre) values (:genre)";

    private final GenericDao<Genre> genericDao;

    private RowMapper<Genre> genreRowMapper = (rs, rowNum) -> new Genre(
            rs.getInt("id"),
            rs.getString("genre")
    );

    @Override
    public Genre insert(Genre genre) {
        int id = genericDao.insert(genre, INSERT_INTO_GENRE);
        Genre genreById;
        try {
            genreById = findById(id);
        } catch (GenreNotFoundException e) {
            throw new CannotInsertException(String.format("Cannot retrieve genre with id = %d after insert", id));
        }
        return genreById;
    }

    @Override
    public Genre findById(int id) throws GenreNotFoundException {
        Optional<Genre> genre = genericDao.findById(id, SELECT_FROM_GENRE_BY_ID, genreRowMapper);
        return genre.orElseThrow(GenreNotFoundException::new);
    }

    @Override
    public Genre update(Genre genre) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return genericDao.delete(id, DELETE_FROM_GENRE_BY_ID);
    }

    @Override
    public List<Genre> getAllGenres() {
        return genericDao.getAll(SELECT_ALL_FROM_GENRE, genreRowMapper);
    }
}
