package ru.otus.springlibrary.repository;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.springlibrary.domain.Author;
import ru.otus.springlibrary.domain.Book;
import ru.otus.springlibrary.domain.Genre;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataMongoTest
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    void save() {
        Author author = new Author(ObjectId.get(), "test first name", "test last name");
        Genre genre = new Genre(ObjectId.get(), "test genre");
        String title = "test title";
        Book book = bookRepository.save(new Book(title, Collections.singletonList(author), Collections.singletonList(genre)));

        assertNotNull(book.getId());
        assertEquals(title, book.getTitle());
        assertTrue(book.getAuthors().contains(author));
        assertTrue(book.getGenres().contains(genre));
    }
}