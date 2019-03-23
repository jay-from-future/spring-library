package ru.otus.springlibrary.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.otus.springlibrary.domain.Genre;

@Repository
@RequiredArgsConstructor
public class GenreDaoImpl implements GenreDao {

    private static final String SELECT_FROM_GENRE_BY_ID = "select * from genre where id = :id";

    private final GenericDao<Genre> genericDao;

    private RowMapper<Genre> genreRowMapper = (rs, rowNum) -> new Genre(
            rs.getInt("id"),
            rs.getString("genre")
    );

    @Override
    public Genre insert(Genre genre) {
        return null;
    }

    @Override
    public Genre findById(int id) {
        return genericDao.findById(id, SELECT_FROM_GENRE_BY_ID, genreRowMapper);
    }

    @Override
    public Genre update(Genre genre) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
