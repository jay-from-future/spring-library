package ru.otus.springlibrary.dao;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.springlibrary.domain.Genre;
import ru.otus.springlibrary.exception.GenreNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@JdbcTest
@ComponentScan
class GenreDaoImplTest {

    private static final String TEST_DB_GENRE = "test db genre";

    @Autowired
    GenreDao genreDao;

    @Test
    void findById() throws GenreNotFoundException {
        int id = 1;
        Genre genre = genreDao.findById(id);

        assertEquals(id, genre.getId());
        assertEquals("test db genre", genre.getGenre());
    }

    @Test
    void getAllGenres() {
        List<Genre> allGenres = genreDao.getAllGenres();

        assertEquals(1, allGenres.size());
        assertEquals(TEST_DB_GENRE, allGenres.get(0).getGenre());
    }

    @Test
    void deleteExisting() {
        assertThrows(DataIntegrityViolationException.class, () -> genreDao.delete(1));
    }

}