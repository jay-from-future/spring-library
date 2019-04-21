package ru.otus.springlibrary.service;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.springlibrary.domain.Author;
import ru.otus.springlibrary.domain.Book;
import ru.otus.springlibrary.domain.Genre;
import ru.otus.springlibrary.domain.Review;
import ru.otus.springlibrary.exception.BookNotFoundException;
import ru.otus.springlibrary.exception.ReviewNotFoundException;
import ru.otus.springlibrary.repository.AuthorRepository;
import ru.otus.springlibrary.repository.BookRepository;
import ru.otus.springlibrary.repository.GenreRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    GenreRepository genreRepository;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();
        genreRepository.deleteAll();
    }

    @Test
    void addBookWithManyAuthorsAndGenres() {
        ObjectId authorId1 = ObjectId.get();
        ObjectId authorId2 = ObjectId.get();
        ObjectId genreId1 = ObjectId.get();
        ObjectId genreId2 = ObjectId.get();

        Author author1 = addAuthor(authorId1, "test first name 1", "test last name 1");
        Author author2 = addAuthor(authorId2, "test first name 2", "test last name 2");

        Genre genre1 = addGenre(genreId1, "test genre 1");
        Genre genre2 = addGenre(genreId2, "test genre 2");

        Book book = addBook(Arrays.asList(authorId1, authorId2), Arrays.asList(genreId1, genreId2));

        Book resultBook = bookRepository.findById(book.getId()).orElseThrow();

        Author resultAuthor1 = getAuthor(authorId1, resultBook);
        Author resultAuthor2 = getAuthor(authorId2, resultBook);

        Genre resultGenre1 = getGenre(genreId1, resultBook);
        Genre resultGenre2 = getGenre(genreId2, resultBook);

        assertEquals(TEST_TITLE, resultBook.getTitle());

        assertEquals(2, book.getAuthors().size());
        assertEquals(author1, resultAuthor1);
        assertEquals(author2, resultAuthor2);

        assertEquals(2, book.getGenres().size());
        assertEquals(genre1, resultGenre1);
        assertEquals(genre2, resultGenre2);
    }

    @Test
    void addReview() {
        ObjectId bookId = createTestBookWithOneAuthorAndOneGenre().getId();
        bookService.addReview(bookId, TEST_REVIEW);

        List<Review> reviews = bookRepository.findById(bookId).orElseThrow().getReviews();
        assertEquals(1, reviews.size());
        assertTrue(reviews.stream().anyMatch(r -> TEST_REVIEW.equals(r.getReview())));
    }


    @Test
    void addReviewIfBookDoesNotExist() {
        assertThrows(BookNotFoundException.class, () -> bookService.addReview(ObjectId.get(), TEST_REVIEW));
    }

    @Test
    void deleteReview() {
        ObjectId bookId = createTestBookWithOneAuthorAndOneGenre().getId();
        ObjectId reviewId = bookService.addReview(bookId, TEST_REVIEW);

        // check that review exist
        List<Review> reviews = bookRepository.findById(bookId).orElseThrow().getReviews();
        assertEquals(1, reviews.size());
        Review actualReview = reviews.get(0);
        assertEquals(reviewId, actualReview.getId());
        assertEquals(TEST_REVIEW, actualReview.getReview());

        assertDoesNotThrow(() -> bookService.deleteReview(bookId, reviewId));

        // check that review has been removed
        assertTrue(bookRepository.findById(bookId).orElseThrow().getReviews().isEmpty());
    }

    @Test
    void deleteReviewIfReviewDoesNotExist() {
        ObjectId bookId = createTestBookWithOneAuthorAndOneGenre().getId();
        assertThrows(ReviewNotFoundException.class, () -> bookService.deleteReview(bookId, ObjectId.get()));
    }

    @Test
    void getAllBooks() {
        ObjectId bookId = createTestBookWithOneAuthorAndOneGenre().getId();

        Iterator<Book> allBooks = bookService.findAll().iterator();
        assertTrue(allBooks.hasNext());
        Book actual = allBooks.next();
        assertEquals(bookId, actual.getId());
        assertEquals(TEST_TITLE, actual.getTitle());
    }

    @Test
    void removeBookWithAuthorAndGenre() {
        ObjectId bookId = createTestBookWithOneAuthorAndOneGenre().getId();

        assertDoesNotThrow(() -> bookService.delete(bookId));
        assertFalse(bookRepository.existsById(bookId));
    }

    private Author addAuthor(ObjectId authorId, String firstName, String lastName) {
        return authorRepository.save(new Author(authorId, firstName, lastName));
    }

    private Genre addGenre(ObjectId genreId, String genre) {
        return genreRepository.save(new Genre(genreId, genre));
    }

    private Author getAuthor(ObjectId authorId, Book book) {
        return book.getAuthors().stream()
                .filter(a -> authorId.equals(a.getId()))
                .findFirst()
                .orElseThrow();
    }

    private Genre getGenre(ObjectId genreId, Book book) {
        return book.getGenres().stream()
                .filter(a -> genreId.equals(a.getId()))
                .findFirst()
                .orElseThrow();
    }

    private Book addBook(List<ObjectId> authorIDs, List<ObjectId> genreIDs) {
        return bookService.addBook(BookServiceImplTest.TEST_TITLE, authorIDs, genreIDs);
    }

    private Book createTestBookWithOneAuthorAndOneGenre() {
        ObjectId authorId = ObjectId.get();
        addAuthor(authorId, "test first name", "test last name");

        ObjectId genreId = ObjectId.get();
        addGenre(genreId, "test genre");

        return addBook(Collections.singletonList(authorId), Collections.singletonList(genreId));
    }
}