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
import ru.otus.springlibrary.domain.Author;
import ru.otus.springlibrary.domain.Book;
import ru.otus.springlibrary.domain.Genre;
import ru.otus.springlibrary.domain.Review;
import ru.otus.springlibrary.exception.AuthorNotFoundException;
import ru.otus.springlibrary.exception.BookNotFoundException;
import ru.otus.springlibrary.exception.GenreNotFoundException;
import ru.otus.springlibrary.exception.ReviewNotFoundException;
import ru.otus.springlibrary.repository.AuthorRepository;
import ru.otus.springlibrary.repository.BookRepository;
import ru.otus.springlibrary.repository.GenreRepository;
import ru.otus.springlibrary.repository.ReviewRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false",
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
})
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

    @MockBean
    ReviewRepository reviewRepository;

    @BeforeEach
    void setUp() throws AuthorNotFoundException, GenreNotFoundException, ReviewNotFoundException {
        Author author1 = new Author(1, "test first name 1", "test last name 1", new ArrayList<>());
        Author author2 = new Author(2, "test first name 2", "test last name 2", new ArrayList<>());

        Genre genre1 = new Genre(1, "test genre 1", new ArrayList<>());
        Genre genre2 = new Genre(2, "test genre 2", new ArrayList<>());

        Review review = new Review(TEST_REVIEW);
        ArrayList<Review> reviews = new ArrayList<>();
        reviews.add(review);

        Book book = new Book(1, TEST_TITLE, Collections.singletonList(author1), Collections.singletonList(genre1),
                reviews);
        review.setBook(book);

        when(bookRepository.findAll()).thenReturn(Collections.singletonList(book));

        // existing items
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author1));
        when(authorRepository.findById(2L)).thenReturn(Optional.of(author2));

        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre1));
        when(genreRepository.findById(2L)).thenReturn(Optional.of(genre2));

        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // items that does not exist
        when(bookRepository.findById(2L)).thenThrow(BookNotFoundException.class);
        when(reviewRepository.findById(2L)).thenThrow(ReviewNotFoundException.class);
    }

    @Test
    void addReview() {
        assertDoesNotThrow(() -> bookService.addReview(1, TEST_REVIEW));
    }

    @Test
    void addReviewIfBookDoesNotExist() {
        assertThrows(BookNotFoundException.class, () -> bookService.addReview(2, TEST_REVIEW));
    }

    @Test
    void deleteReview() {
        assertDoesNotThrow(() -> bookService.deleteReview(1));
    }

    @Test
    void deleteReviewIfReviewDoesNotExist() {
        assertThrows(ReviewNotFoundException.class, () -> bookService.deleteReview(2));
    }

    @Test
    void getAllBooks() {
        Iterator<Book> allBooks = bookService.findAll().iterator();

        assertTrue(allBooks.hasNext());
        Book actual = allBooks.next();
        assertEquals(TEST_TITLE, actual.getTitle());
    }

    @Test
    void addBookWithAuthorAndGenre() {
        List<Long> authorIDs = Arrays.asList(1L, 2L);
        List<Long> genreIDs = Arrays.asList(1L, 2L);
        String title = "add new book test title";

        assertDoesNotThrow(() -> bookService.addBook(title, authorIDs, genreIDs));
    }

    @Test
    void removeBookWithAuthorAndGenre() {
        assertDoesNotThrow(() -> bookService.delete(1));
    }
}