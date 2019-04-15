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
import ru.otus.springlibrary.repository.AuthorRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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

    @MockBean
    AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        Author author = new Author(1, FIRST_NAME, LAST_NAME, new ArrayList<>());
        when(authorRepository.findAll()).thenReturn(Collections.singletonList(author));
    }

    @Test
    void getAllAuthors() {
        Iterator<Author> allAuthors = authorService.findAll().iterator();

        assertTrue(allAuthors.hasNext());
        Author author = allAuthors.next();
        assertEquals(FIRST_NAME, author.getFirstName());
        assertEquals(LAST_NAME, author.getLastName());
    }

    @Test
    void addNewValidAuthor() {
        assertDoesNotThrow(() -> authorService.addAuthor(FIRST_NAME, LAST_NAME));
    }

}