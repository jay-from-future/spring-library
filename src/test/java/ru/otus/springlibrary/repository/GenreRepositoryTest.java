package ru.otus.springlibrary.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.springlibrary.domain.Genre;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@DataMongoTest
class GenreRepositoryTest {

    @Autowired
    GenreRepository genreRepository;

    @Test
    void save() {
        String testGenre = "test genre";
        Genre genre = genreRepository.save(new Genre(testGenre));
        assertNotNull(genre.getId());
        assertEquals(testGenre, genre.getGenre());
    }
}