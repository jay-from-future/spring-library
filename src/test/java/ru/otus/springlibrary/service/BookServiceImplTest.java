package ru.otus.springlibrary.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.springlibrary.BasicTest;
import ru.otus.springlibrary.dao.AuthorDao;
import ru.otus.springlibrary.dao.BookDao;
import ru.otus.springlibrary.dao.GenreDao;
import ru.otus.springlibrary.domain.Author;
import ru.otus.springlibrary.domain.Book;
import ru.otus.springlibrary.domain.Genre;
import ru.otus.springlibrary.exception.AuthorNotFoundException;
import ru.otus.springlibrary.exception.GenreNotFoundException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class BookServiceImplTest extends BasicTest {

    private static final String TEST_TITLE = "test title";

    @Autowired
    BookService bookService;

    @MockBean
    BookDao bookDao;

    @MockBean
    AuthorDao authorDao;

    @MockBean
    GenreDao genreDao;

    private Book book;

    @BeforeEach
    void setUp() throws AuthorNotFoundException, GenreNotFoundException {
        Author author = new Author(1, "test first name", "test last name");
        Genre genre = new Genre(1, "test genre");
        book = new Book(1, TEST_TITLE, author, genre);

        when(bookDao.getAllBooks()).thenReturn(Collections.singletonList(book));
        // existing items
        when(authorDao.findById(1)).thenReturn(author);
        when(genreDao.findById(1)).thenReturn(genre);

        // items that does not exist
        when(authorDao.findById(2)).thenThrow(AuthorNotFoundException.class);
        when(genreDao.findById(2)).thenThrow(GenreNotFoundException.class);
    }

    @Test
    void getAllBooks() {
        List<Book> allBooks = bookService.getAllBooks();

        assertEquals(1, allBooks.size());
        Book actual = allBooks.get(0);
        assertEquals(TEST_TITLE, actual.getTitle());
    }

    @Test
    void addBook() {
        when(bookDao.insert(any())).thenReturn(book);
        assertTrue(bookService.addBook(TEST_TITLE, 2, 2));
    }

}