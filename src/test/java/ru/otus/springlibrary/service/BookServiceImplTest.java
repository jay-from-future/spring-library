package ru.otus.springlibrary.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.springlibrary.BasicTest;
import ru.otus.springlibrary.dao.BookDao;
import ru.otus.springlibrary.domain.Author;
import ru.otus.springlibrary.domain.Book;
import ru.otus.springlibrary.domain.Genre;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class BookServiceImplTest extends BasicTest {

    private static final String TEST_TITLE = "test title";

    @Autowired
    BookService bookService;

    @MockBean
    BookDao bookDao;

    @BeforeEach
    void setUp() {
        Author author = new Author(1, "test first name", "test last name");
        Genre genre = new Genre(1, "test genre");
        Book book = new Book(1, TEST_TITLE, author, genre);

        when(bookDao.getAllBooks()).thenReturn(Collections.singletonList(book));
    }

    @Test
    void getAllBooks() {
        List<Book> allBooks = bookService.getAllBooks();

        assertEquals(1, allBooks.size());
        Book actual = allBooks.get(0);
        assertEquals(TEST_TITLE, actual.getTitle());
    }
}