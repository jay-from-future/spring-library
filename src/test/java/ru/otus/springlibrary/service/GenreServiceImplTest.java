package ru.otus.springlibrary.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.springlibrary.dao.GenreDao;
import ru.otus.springlibrary.domain.Genre;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false",
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
})
class GenreServiceImplTest {

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