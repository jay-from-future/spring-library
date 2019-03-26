package ru.otus.springlibrary.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.springlibrary.BasicTest;
import ru.otus.springlibrary.domain.Author;
import ru.otus.springlibrary.domain.Book;
import ru.otus.springlibrary.domain.Genre;
import ru.otus.springlibrary.exception.AuthorNotFoundException;
import ru.otus.springlibrary.exception.BookNotFoundException;
import ru.otus.springlibrary.exception.GenreNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BookDaoImplTest extends BasicTest {

    private static final String TEST_DB_TITLE = "test db title";

    @Autowired
    BookDaoImpl bookDao;

    @MockBean
    AuthorDaoImpl authorDao;

    @MockBean
    GenreDaoImpl genreDao;

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
    void getAllBooks() {
        List<Book> allBooks = bookDao.getAllBooks();

        assertEquals(1L, allBooks.size());
        Book actual = allBooks.get(0);
        assertEquals(TEST_DB_TITLE, actual.getTitle());
    }

}