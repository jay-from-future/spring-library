package ru.otus.springlibrary.dao;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.springlibrary.domain.Genre;
import ru.otus.springlibrary.exception.GenreNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import({GenreDaoImpl.class, GenericDaoImpl.class})
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class GenreDaoImplTest {

    private static final long EXISTING_GENRE_WITH_BOOK_REFERENCE_ID = 1;

    @Autowired
    GenreDao genreDao;

    @Test
    void findExistingGenreById() throws GenreNotFoundException {
        Genre genre = genreDao.findById(EXISTING_GENRE_WITH_BOOK_REFERENCE_ID);

        assertEquals(EXISTING_GENRE_WITH_BOOK_REFERENCE_ID, genre.getId());
        assertEquals("test db genre", genre.getGenre());
    }

    @Test
    void getAllGenres() {
        List<Genre> allGenres = genreDao.getAllGenres();
        assertFalse(allGenres.isEmpty());
    }

    @Test
    void delete() {
        String testNewGenre = "test new genre for delete";
        Genre genre = new Genre(testNewGenre);
        genreDao.insert(genre);

        long id = genre.getId();
        assertFalse(id == 0);
        assertEquals(testNewGenre, genre.getGenre());

        genreDao.delete(genre);

        assertThrows(GenreNotFoundException.class, () -> genreDao.findById(id));
    }

    @Test
    void insertThenUpdateAndFindByIdForCheck() throws GenreNotFoundException {
        String testNewGenre = "test new genre for insert";
        Genre genre = new Genre(testNewGenre);
        genreDao.insert(genre);

        long id = genre.getId();
        assertFalse(id == 0);
        assertEquals(testNewGenre, genre.getGenre());

        String testUpdatedGenre = "test genre for update";
        genre.setGenre(testUpdatedGenre);
        genreDao.update(genre);

        Genre genreById = genreDao.findById(id);
        assertEquals(id, genreById.getId());
        assertEquals(testUpdatedGenre, genreById.getGenre());
    }
}