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
import ru.otus.springlibrary.domain.Genre;
import ru.otus.springlibrary.repository.GenreRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    GenreRepository genreRepository;

    @BeforeEach
    void setUp() {
        Genre genre = new Genre(1, TEST_GENRE, new ArrayList<>());
        when(genreRepository.findAll()).thenReturn(Collections.singletonList(genre));
    }

    @Test
    void getAllGenres() {
        Iterator<Genre> allGenres = genreService.findAll().iterator();

        assertTrue(allGenres.hasNext());
        Genre actual = allGenres.next();
        assertEquals(TEST_GENRE, actual.getGenre());
    }
}