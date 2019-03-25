package ru.otus.springlibrary.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import ru.otus.springlibrary.dao.AuthorDao;
import ru.otus.springlibrary.domain.Author;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private static final Logger logger = LoggerFactory.getLogger(AuthorService.class);

    private final AuthorDao authorDao;

    @Override
    public List<Author> getAllAuthors() {
        return authorDao.getAllAuthors();
    }

    @Override
    public boolean addAuthor(String firstName, String lastName) {
        try {
            authorDao.insert(new Author(firstName, lastName));
            return true;
        } catch (DuplicateKeyException e) {
            logger.debug(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        boolean result = false;
        try {
            result = authorDao.delete(id);
        } catch (DataAccessException e) {
            logger.debug(e.getMessage());
        }
        return result;
    }
}
