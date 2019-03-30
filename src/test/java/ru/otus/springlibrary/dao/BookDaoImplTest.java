package ru.otus.springlibrary.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.springlibrary.domain.Author;
import ru.otus.springlibrary.domain.Book;
import ru.otus.springlibrary.domain.Genre;
import ru.otus.springlibrary.exception.AuthorNotFoundException;
import ru.otus.springlibrary.exception.BookNotFoundException;
import ru.otus.springlibrary.exception.GenreNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import({BookDaoImpl.class, GenericDaoImpl.class})
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class BookDaoImplTest {

    private static final String TEST_DB_TITLE = "test db title";

    @Autowired
    BookDao bookDao;

    @MockBean
    AuthorDao authorDao;

    @MockBean
    GenreDao genreDao;

    @BeforeEach
    void setUp() throws AuthorNotFoundException, GenreNotFoundException {
        Author author = mock(Author.class);
        Genre genre = mock(Genre.class);

        when(author.getId()).thenReturn(1L);
        when(authorDao.findById(1)).thenReturn(author);

        when(genre.getId()).thenReturn(1L);
        when(genreDao.findById(1)).thenReturn(genre);
    }

    @Test
    void findById() throws BookNotFoundException {
        long id = 1L;
        Book book = bookDao.findById(id);

        assertEquals(id, book.getId());
        assertEquals(TEST_DB_TITLE, book.getTitle());
    }

    @Test
    void deleteExistingBook() throws BookNotFoundException {
        Book book = bookDao.findById(1);
        bookDao.delete(book);
        assertThrows(BookNotFoundException.class, () -> bookDao.findById(1));
    }
}