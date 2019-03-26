package ru.otus.springlibrary.dao;

import com.google.common.base.Strings;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.springlibrary.domain.Author;
import ru.otus.springlibrary.exception.AuthorNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@JdbcTest
@ComponentScan
class AuthorDaoImplTest {

    private static final String FIRST_NAME = "test insert first name";

    private static final String LAST_NAME = "test insert last name";

    private static final String TEST_DB_FIRST_NAME = "test db first name";

    private static final String TEST_DB_LAST_NAME = "test db last name";

    @Autowired
    AuthorDaoImpl authorDao;

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
        assertThrows(AuthorNotFoundException.class, () -> authorDao.findById(Integer.MAX_VALUE));
    }

    @Test
    void insertValidAuthor() {
        Author author = new Author(FIRST_NAME, LAST_NAME);
        Author authorWithGeneratedId = authorDao.insert(author);

        assertTrue(authorWithGeneratedId.getId() != 0);
        assertEquals(FIRST_NAME, authorWithGeneratedId.getFirstName());
        assertEquals(LAST_NAME, authorWithGeneratedId.getLastName());
    }

    @Test
    void insertAuthorWithTooLongFirstName() {
        Author authorWithTooLongFirstName = new Author(Strings.repeat("A", 256), LAST_NAME);
        assertThrows(DataIntegrityViolationException.class, () -> authorDao.insert(authorWithTooLongFirstName));
    }

    @Test
    void tryToInsertExistingAuthor() {
        Author author = new Author(TEST_DB_FIRST_NAME, TEST_DB_LAST_NAME);
        assertThrows(DuplicateKeyException.class, () -> authorDao.insert(author));
    }

    @Test
    void update() {
        String testUpdateFirstName = "test update first name";
        String testUpdateLastName = "test update last name";

        Author author = authorDao.insert(new Author(FIRST_NAME, LAST_NAME));
        author.setFirstName(testUpdateFirstName);
        author.setLastName(testUpdateLastName);
        Author updatedAuthor = authorDao.update(author);

        assertTrue(author.getId() != 0);
        assertEquals(author.getId(), updatedAuthor.getId());
        assertEquals(testUpdateFirstName, updatedAuthor.getFirstName());
        assertEquals(testUpdateLastName, updatedAuthor.getLastName());
    }

    @Test
    void deleteNew() {
        Author author = new Author(FIRST_NAME, LAST_NAME);
        long id = authorDao.insert(author).getId();
        boolean deleteResult = authorDao.delete(id);
        boolean deleteAgainResult = authorDao.delete(id);

        assertTrue(id != 0);
        assertTrue(deleteResult);
        assertFalse(deleteAgainResult);
        assertThrows(Exception.class, () -> authorDao.findById(id));
    }

    @Test
    void deleteExisting() {
        assertThrows(DataIntegrityViolationException.class, () -> authorDao.delete(1));
    }

    @Test
    void getAllAuthors() {
        List<Author> allAuthors = authorDao.getAllAuthors();

        assertFalse(allAuthors.isEmpty());
        Author actual = allAuthors.get(0);
        assertEquals(TEST_DB_FIRST_NAME, actual.getFirstName());
        assertEquals(TEST_DB_LAST_NAME, actual.getLastName());
    }
}