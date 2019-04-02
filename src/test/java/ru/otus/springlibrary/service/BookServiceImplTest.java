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
import ru.otus.springlibrary.dao.AuthorDao;
import ru.otus.springlibrary.dao.BookDao;
import ru.otus.springlibrary.dao.GenreDao;
import ru.otus.springlibrary.dao.ReviewDao;
import ru.otus.springlibrary.domain.Author;
import ru.otus.springlibrary.domain.Book;
import ru.otus.springlibrary.domain.Genre;
import ru.otus.springlibrary.domain.Review;
import ru.otus.springlibrary.exception.AuthorNotFoundException;
import ru.otus.springlibrary.exception.BookNotFoundException;
import ru.otus.springlibrary.exception.GenreNotFoundException;
import ru.otus.springlibrary.exception.ReviewNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    BookDao bookDao;

    @MockBean
    AuthorDao authorDao;

    @MockBean
    GenreDao genreDao;

    @MockBean
    ReviewDao reviewDao;

    @BeforeEach
    void setUp() throws AuthorNotFoundException, GenreNotFoundException, BookNotFoundException, ReviewNotFoundException {
        Author author = new Author(1, "test first name", "test last name");
        Genre genre = new Genre(1, "test genre");

        Review review = new Review(TEST_REVIEW);
        ArrayList<Review> reviews = new ArrayList<>();
        reviews.add(review);

        Book book = new Book(1, TEST_TITLE, author, genre, reviews);
        review.setBook(book);

        when(bookDao.getAllBooks()).thenReturn(Collections.singletonList(book));
        // existing items
        when(authorDao.findById(1)).thenReturn(author);
        when(genreDao.findById(1)).thenReturn(genre);
        when(reviewDao.findById(1)).thenReturn(review);
        when(bookDao.findById(1)).thenReturn(book);

        // items that does not exist
        when(authorDao.findById(2)).thenThrow(AuthorNotFoundException.class);
        when(genreDao.findById(2)).thenThrow(GenreNotFoundException.class);
        when(bookDao.findById(2)).thenThrow(BookNotFoundException.class);
        when(reviewDao.findById(2)).thenThrow(ReviewNotFoundException.class);
    }

    @Test
    void addReview() {
        assertTrue(bookService.addReview(1, TEST_REVIEW));
    }

    @Test
    void addReviewIfBookDoesNotExist() {
        assertFalse(bookService.addReview(2, TEST_REVIEW));
    }

    @Test
    void deleteReview() {
        assertTrue(bookService.deleteReview(1));
    }

    @Test
    void deleteReviewIfReviewDoesNotExist() {
        assertFalse(bookService.deleteReview(2));
    }

    @Test
    void getAllBooks() {
        List<Book> allBooks = bookService.getAllBooks();

        assertEquals(1, allBooks.size());
        Book actual = allBooks.get(0);
        assertEquals(TEST_TITLE, actual.getTitle());
    }
}