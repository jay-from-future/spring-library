package ru.otus.springlibrary.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.otus.springlibrary.dao.AuthorDao;
import ru.otus.springlibrary.domain.Author;
import ru.otus.springlibrary.exception.AuthorNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private static final Logger logger = LoggerFactory.getLogger(AuthorServiceImpl.class);

    private final AuthorDao authorDao;

    @Override
    public List<Author> getAllAuthors() {
        return authorDao.getAllAuthors();
    }

    @Override
    public boolean addAuthor(String firstName, String lastName) {
        try {
            Author author = new Author(firstName, lastName);
            authorDao.insert(author);
            return true;
        } catch (DataIntegrityViolationException e) {
            logger.debug(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        try {
            Author author = authorDao.findById(id);
            authorDao.delete(author);
        } catch (AuthorNotFoundException | DataIntegrityViolationException e) {
            logger.debug("Cannot remove author with id = " + id, e);
            return false;
        }
        return true;
    }
}
