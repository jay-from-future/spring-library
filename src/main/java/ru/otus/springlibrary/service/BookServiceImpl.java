package ru.otus.springlibrary.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.otus.springlibrary.dao.BookDao;
import ru.otus.springlibrary.domain.Author;
import ru.otus.springlibrary.domain.Book;
import ru.otus.springlibrary.domain.Genre;
import ru.otus.springlibrary.exception.BookNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookDao bookDao;

    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAllBooks();
    }

    @Override
    public boolean addBook(String title, long authorId, long genreId) {
        try {
            // todo add check that book existing or not before perform insert, otherwise id will be incremented for useless attempts
            bookDao.insert(new Book(title, new Author(authorId), new Genre(genreId)));
            return true;
        } catch (DataIntegrityViolationException e) {
            logger.debug(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        try {
            Book book = bookDao.findById(id);
            bookDao.delete(book);
        } catch (BookNotFoundException | DataIntegrityViolationException e) {
            logger.debug("Cannot remove book with id = " + id, e);
            return false;
        }
        return true;
    }
}
