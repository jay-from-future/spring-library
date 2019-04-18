package ru.otus.springlibrary.service;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.springlibrary.domain.Author;
import ru.otus.springlibrary.domain.Book;
import ru.otus.springlibrary.domain.Genre;
import ru.otus.springlibrary.exception.BookNotFoundException;
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

    private ObjectId authorId1;

    private ObjectId authorId2;

    private ObjectId bookId;

    private ObjectId genreId1;

    private ObjectId genreId2;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();
        genreRepository.deleteAll();

        authorId1 = ObjectId.get();
        authorId2 = ObjectId.get();

        Author author1 = new Author(authorId1, "test first name 1", "test last name 1");
        Author author2 = new Author(authorId2, "test first name 2", "test last name 2");

        authorRepository.save(author1);
        authorRepository.save(author2);

        genreId1 = ObjectId.get();
        genreId2 = ObjectId.get();

        Genre genre1 = new Genre(genreId1, "test genre 1");
        Genre genre2 = new Genre(genreId2, "test genre 2");

        genreRepository.save(genre1);
        genreRepository.save(genre2);

        bookId = ObjectId.get();

        Book book = new Book(bookId, TEST_TITLE, Collections.singletonList(author1), Collections.singletonList(genre1),
                Collections.singletonList(TEST_REVIEW));

        bookRepository.save(book);
    }

    @Test
    void addReview() {
        bookService.addReview(bookId, TEST_REVIEW);
        Book book = bookRepository.findById(bookId).orElseThrow();
        assertTrue(book.getReviews().stream().anyMatch(TEST_REVIEW::equals));
    }

    @Test
    void addReviewIfBookDoesNotExist() {
        assertThrows(BookNotFoundException.class, () -> bookService.addReview(ObjectId.get(), TEST_REVIEW));
    }

//    @Test
//    void deleteReview() {
//        assertDoesNotThrow(() -> bookService.deleteReview(1));
//    }

//    @Test
//    void deleteReviewIfReviewDoesNotExist() {
//        assertThrows(ReviewNotFoundException.class, () -> bookService.deleteReview(ObjectId.get()));
//    }

    @Test
    void getAllBooks() {
        Iterator<Book> allBooks = bookService.findAll().iterator();

        assertTrue(allBooks.hasNext());
        Book actual = allBooks.next();
        assertEquals(TEST_TITLE, actual.getTitle());
    }

    @Test
    void addBookWithAuthorAndGenre() {
        List<ObjectId> authorIDs = Arrays.asList(authorId1, authorId2);
        List<ObjectId> genreIDs = Arrays.asList(genreId1, genreId2);
        String title = "add new book test title";

        assertDoesNotThrow(() -> bookService.addBook(title, authorIDs, genreIDs));
        Book book = bookRepository.findOne(Example.of(new Book(title))).orElseThrow();

        assertNotNull(book.getId());
        assertEquals(title, book.getTitle());

        // todo check author and genres
    }

    @Test
    void removeBookWithAuthorAndGenre() {
        assertDoesNotThrow(() -> bookService.delete(bookId));
    }
}