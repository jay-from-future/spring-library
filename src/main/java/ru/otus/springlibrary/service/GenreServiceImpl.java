package ru.otus.springlibrary.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import ru.otus.springlibrary.dao.GenreDao;
import ru.otus.springlibrary.domain.Genre;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private static final Logger logger = LoggerFactory.getLogger(GenreServiceImpl.class);

    private final GenreDao genreDao;

    @Override
    public List<Genre> getAllGenres() {
        return genreDao.getAllGenres();
    }

    @Override
    public boolean addGenre(String genre) {
        try {
            genreDao.insert(new Genre(genre));
            return true;
        } catch (DuplicateKeyException e) {
            logger.debug(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        boolean result = false;
        try {
            result = genreDao.delete(id);
        } catch (DataAccessException e) {
            logger.debug(e.getMessage());
        }
        return result;
    }

}
