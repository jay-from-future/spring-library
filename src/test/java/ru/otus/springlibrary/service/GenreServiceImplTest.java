package ru.otus.springlibrary.service;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.springlibrary.domain.Genre;
import ru.otus.springlibrary.repository.GenreRepository;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false",
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
})
class GenreServiceImplTest {

    private static final String TEST_GENRE = "test genre";

    @Autowired
    GenreService genreService;

    @Autowired
    GenreRepository genreRepository;

    @BeforeEach
    void setUp() {
        genreRepository.deleteAll();
        ObjectId genreId = ObjectId.get();
        Genre genre = new Genre(genreId, TEST_GENRE);
        genreRepository.save(genre);
    }

    @Test
    void getAllGenres() {
        Iterator<Genre> allGenres = genreService.findAll().iterator();

        assertTrue(allGenres.hasNext());
        Genre actual = allGenres.next();
        assertEquals(TEST_GENRE, actual.getGenre());
    }
}