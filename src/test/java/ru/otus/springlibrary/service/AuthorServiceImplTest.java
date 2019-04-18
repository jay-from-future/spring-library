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
import ru.otus.springlibrary.exception.AuthorNotFoundException;
import ru.otus.springlibrary.repository.AuthorRepository;

import java.util.Iterator;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false",
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
})
class AuthorServiceImplTest {

    private static final String FIRST_NAME = "test first name";

    private static final String LAST_NAME = "test last name";

    @Autowired
    AuthorService authorService;

    @Autowired
    AuthorRepository authorRepository;

    private ObjectId authorId;

    @BeforeEach
    void setUp() {
        authorRepository.deleteAll();
        authorId = ObjectId.get();
        Author author = new Author(authorId, FIRST_NAME, LAST_NAME);
        authorRepository.save(author);
    }

    @Test
    void getAllAuthors() {
        Iterator<Author> allAuthors = authorService.findAll().iterator();

        assertTrue(allAuthors.hasNext());
        Author author = allAuthors.next();
        assertEquals(authorId, author.getId());
        assertEquals(FIRST_NAME, author.getFirstName());
        assertEquals(LAST_NAME, author.getLastName());
    }

    @Test
    void addAuthor() {
        String firstName = "test first name new valid author";
        String lastName = "test last name new valid author";
        authorService.addAuthor(firstName, lastName);

        Optional<Author> author = authorRepository.findOne(Example.of(new Author(firstName, lastName)));
        assertTrue(author.isPresent());
    }

    @Test
    void deleteExistingAuthor() {
        assertTrue(authorRepository.existsById(authorId));
        authorService.delete(authorId);
        assertFalse(authorRepository.existsById(authorId));
    }

    @Test
    void deleteNotExistingAuthor() {
        ObjectId notExistingAuthorId = ObjectId.get();
        assertFalse(authorRepository.existsById(notExistingAuthorId));
        assertThrows(AuthorNotFoundException.class, () -> authorService.delete(notExistingAuthorId));
    }
}