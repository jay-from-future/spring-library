package ru.otus.springlibrary.service;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.springlibrary.TestApplicationConfiguration;
import ru.otus.springlibrary.domain.Author;
import ru.otus.springlibrary.exception.AuthorNotFoundException;
import ru.otus.springlibrary.repository.AuthorRepository;

import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false",
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
})
@ContextConfiguration(classes = TestApplicationConfiguration.class)
class AuthorServiceImplTest {

    private static final String FIRST_NAME = "test first name";

    private static final String LAST_NAME = "test last name";

    @Autowired
    AuthorService authorService;

    @MockBean
    AuthorRepository authorRepository;

    private ObjectId authorId;

    @BeforeEach
    void setUp() {
        authorId = ObjectId.get();
        Author author = new Author(authorId, FIRST_NAME, LAST_NAME);

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(authorRepository.save(any(Author.class))).thenReturn(author);
        when(authorRepository.findAll()).thenReturn(Collections.singletonList(author));
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
        assertNotNull(authorService.addAuthor(FIRST_NAME, LAST_NAME));
    }

    @Test
    void deleteExistingAuthor() {
        authorService.delete(authorId);
    }

    @Test
    void deleteNotExistingAuthor() {
        ObjectId notExistingAuthorId = ObjectId.get();
        assertThrows(AuthorNotFoundException.class, () -> authorService.delete(notExistingAuthorId));
    }
}