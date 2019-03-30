package ru.otus.springlibrary.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import ru.otus.springlibrary.dao.GenreDao;
import ru.otus.springlibrary.domain.Genre;
import ru.otus.springlibrary.exception.GenreNotFoundException;

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
            // todo add check that genre existing or not before perform insert, otherwise id will be incremented for useless attempts
            genreDao.insert(new Genre(genre));
            return true;
        } catch (DataIntegrityViolationException e) {
            logger.debug(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        try {
            Genre genre = genreDao.findById(id);
            genreDao.delete(genre);
        } catch (GenreNotFoundException | DataIntegrityViolationException e) {
            logger.debug("Cannot remove genre with id = " + id, e);
            return false;
        }
        return true;
    }

}
