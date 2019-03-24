package ru.otus.springlibrary.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.springlibrary.BasicTest;
import ru.otus.springlibrary.domain.Author;
import ru.otus.springlibrary.domain.Book;
import ru.otus.springlibrary.domain.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BookDaoImplTest extends BasicTest {

    private static final String TEST_DB_TITLE = "test db title";

    private static final String TEST_BOOK_TITLE = "test book title";

    @Autowired
    BookDaoImpl bookDao;

    @MockBean
    AuthorDaoImpl authorDao;

    @MockBean
    GenreDaoImpl genreDao;

    private Book book;

    private Author author;

    private Genre genre;

    @BeforeEach
    void setUp() {
        author = mock(Author.class);
        genre = mock(Genre.class);

        when(author.getId()).thenReturn(1);
        when(authorDao.findById(1)).thenReturn(author);

        when(genre.getId()).thenReturn(1);
        when(genreDao.findById(1)).thenReturn(genre);

        book = new Book(TEST_BOOK_TITLE, author, genre);
    }

//    @Test
//    void insert() {
//        Book bookFromDB = bookDao.insert(book);
//
//        checkAutoGeneratedId(bookFromDB.getId());
//        assertEquals(TEST_BOOK_TITLE, book.getTitle());
//        assertEquals(author, book.getAuthor());
//        assertEquals(genre, book.getGenre());
//    }

    @Test
    void findById() {
        int id = 1;
        Book book = bookDao.findById(id);

        assertEquals(id, book.getId());
        assertEquals(TEST_DB_TITLE, book.getTitle());
    }

    @Test
    void getAllBooks() {
        List<Book> allBooks = bookDao.getAllBooks();

        assertEquals(1, allBooks.size());
        Book actual = allBooks.get(0);
        assertEquals(TEST_DB_TITLE, actual.getTitle());
    }
}