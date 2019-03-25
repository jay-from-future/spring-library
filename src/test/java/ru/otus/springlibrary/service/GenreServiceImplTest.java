package ru.otus.springlibrary.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.springlibrary.BasicTest;
import ru.otus.springlibrary.dao.GenreDao;
import ru.otus.springlibrary.domain.Genre;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class GenreServiceImplTest extends BasicTest {

    private static final String TEST_GENRE = "test genre";

    @Autowired
    GenreService genreService;

    @MockBean
    GenreDao genreDao;

    @BeforeEach
    void setUp() {
        Genre genre = new Genre(1, TEST_GENRE);
        when(genreDao.getAllGenres()).thenReturn(Collections.singletonList(genre));
    }

    @Test
    void getAllGenres() {
        List<Genre> allGenres = genreService.getAllGenres();

        assertEquals(1, allGenres.size());
        Genre actual = allGenres.get(0);
        assertEquals(TEST_GENRE, actual.getGenre());
    }
}