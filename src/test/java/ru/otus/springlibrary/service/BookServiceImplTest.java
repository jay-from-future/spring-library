package ru.otus.springlibrary.service;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.springlibrary.TestApplicationConfiguration;
import ru.otus.springlibrary.domain.Author;
import ru.otus.springlibrary.domain.Book;
import ru.otus.springlibrary.domain.Genre;
import ru.otus.springlibrary.domain.Review;
import ru.otus.springlibrary.repository.AuthorRepository;
import ru.otus.springlibrary.repository.BookRepository;
import ru.otus.springlibrary.repository.GenreRepository;

import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TestApplicationConfiguration.class)
class BookServiceImplTest {

    private static final String TEST_TITLE = "test title";

    private static final String TEST_REVIEW = "test review";

    @Autowired
    BookService bookService;

    @MockBean
    BookRepository bookRepository;

    @MockBean
    AuthorRepository authorRepository;

    @MockBean
    GenreRepository genreRepository;

    private ObjectId bookId;

    @BeforeEach
    void setUp() {
        ObjectId authorId = ObjectId.get();
        Author author = new Author(authorId, "test first name", "test last name");
        when(authorRepository.findById(authorId))
                .thenReturn(Optional.of(author));

        ObjectId genreId = ObjectId.get();
        Genre genre = new Genre(genreId, "test genre");
        when(genreRepository.findById(genreId)).thenReturn(Optional.of(genre));

        Review review = new Review(ObjectId.get(), TEST_REVIEW);
        bookId = ObjectId.get();
        Book book = new Book(bookId, TEST_TITLE, Collections.singletonList(author),
                Collections.singletonList(genre), Collections.singletonList(review));

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(bookRepository.findAll()).thenReturn(Collections.singletonList(book));
    }

    @Test
    void getAllBooks() {
        Iterator<Book> allBooks = bookService.findAll().iterator();
        assertTrue(allBooks.hasNext());
        Book actual = allBooks.next();
        assertEquals(bookId, actual.getId());
        assertEquals(TEST_TITLE, actual.getTitle());
    }

    @Test
    void removeBookWithAuthorAndGenre() {
        assertDoesNotThrow(() -> bookService.delete(bookId));
    }

}