package ru.otus.springlibrary.dao;

import com.google.common.base.Strings;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.springlibrary.domain.Author;
import ru.otus.springlibrary.exception.AuthorNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import({AuthorDaoImpl.class, GenericDaoImpl.class})
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class AuthorDaoImplTest {

    private static final String FIRST_NAME = "test insert first name";

    private static final String LAST_NAME = "test insert last name";

    private static final String TEST_DB_FIRST_NAME = "test db first name";

    private static final String TEST_DB_LAST_NAME = "test db last name";

    @Autowired
    AuthorDao authorDao;

    @Test
    void findAuthorById() throws AuthorNotFoundException {
        int id = 1;
        Author author = authorDao.findById(id);

        assertEquals(id, author.getId());
        assertEquals(TEST_DB_FIRST_NAME, author.getFirstName());
        assertEquals(TEST_DB_LAST_NAME, author.getLastName());
    }

    @Test
    void findAuthorByWrongId() {
        assertThrows(AuthorNotFoundException.class, () -> authorDao.findById(Long.MAX_VALUE));
    }

    @Test
    void insertValidAuthor() throws AuthorNotFoundException {
        Author author = new Author(FIRST_NAME, LAST_NAME);
        authorDao.insert(author);

        long id = author.getId();
        assertFalse(id == 0);

        Author authorById = authorDao.findById(id);
        assertEquals(FIRST_NAME, authorById.getFirstName());
        assertEquals(LAST_NAME, authorById.getLastName());
    }

    @Test
    void insertAuthorWithTooLongFirstName() {
        Author authorWithTooLongFirstName = new Author(Strings.repeat("A", 256), LAST_NAME);
        assertThrows(DataIntegrityViolationException.class, () -> authorDao.insert(authorWithTooLongFirstName));
    }

    @Test
    void tryToInsertExistingAuthor() {
        Author newAuthor = new Author(TEST_DB_FIRST_NAME, TEST_DB_LAST_NAME);
        assertThrows(DataIntegrityViolationException.class, () -> authorDao.insert(newAuthor));
    }

    @Test
    void deleteExisting() {
        Author author = new Author(TEST_DB_FIRST_NAME, TEST_DB_LAST_NAME);
        assertThrows(DataIntegrityViolationException.class, () -> authorDao.delete(author));
    }

    @Test
    void getAllAuthors() {
        List<Author> allAuthors = authorDao.getAllAuthors();
        assertFalse(allAuthors.isEmpty());
    }

    @Test
    void delete() {
        String firstName = "test new author first name for delete";
        String lastName = "test new author last name for delete";
        Author author = new Author(firstName, lastName);
        authorDao.insert(author);

        long id = author.getId();
        assertFalse(id == 0);
        assertEquals(firstName, author.getFirstName());
        assertEquals(lastName, author.getLastName());

        authorDao.delete(author);

        assertThrows(AuthorNotFoundException.class, () -> authorDao.findById(id));
    }
}