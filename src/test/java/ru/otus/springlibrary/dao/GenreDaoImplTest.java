package ru.otus.springlibrary.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.springlibrary.BasicTest;
import ru.otus.springlibrary.domain.Book;
import ru.otus.springlibrary.domain.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GenreDaoImplTest extends BasicTest {

    private static final String TEST_GENRE = "test genre";

    private static final String TEST_DB_GENRE = "test db genre";

    @Autowired
    GenreDaoImpl genreDao;

    @Test
    void findById() {
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

//    @Test
//    void insert() {
//        Genre genre = new Genre(TEST_GENRE);
//        Genre genreFromDB = genreDao.insert(genre);
//
//        checkAutoGeneratedId(genreFromDB.getId());
//        assertEquals(TEST_GENRE, genreFromDB.getGenre());
//    }
}