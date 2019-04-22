package ru.otus.springlibrary.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.springlibrary.TestApplicationConfiguration;
import ru.otus.springlibrary.domain.Author;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@DataMongoTest
@ContextConfiguration(classes = TestApplicationConfiguration.class)
class AuthorRepositoryTest {

    @Autowired
    AuthorRepository authorRepository;

    @Test
    void save() {
        String lastName = "test last name";
        String firstName = "test first name";
        Author author = authorRepository.save(new Author(firstName, lastName));

        assertNotNull(author.getId());
        assertEquals(firstName, author.getFirstName());
        assertEquals(lastName, author.getLastName());
    }
}