package ru.otus.springlibrary.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import ru.otus.springlibrary.dao.BookDao;
import ru.otus.springlibrary.domain.Author;
import ru.otus.springlibrary.domain.Book;
import ru.otus.springlibrary.domain.Genre;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private static final Logger logger = LoggerFactory.getLogger(GenreServiceImpl.class);

    private final BookDao bookDao;

    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAllBooks();
    }

    @Override
    public boolean addBook(String title, int authorId, int genreId) {
        try {
            bookDao.insert(new Book(title, new Author(authorId), new Genre(genreId)));
            return true;
        } catch (DuplicateKeyException e) {
            logger.debug(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        return bookDao.delete(id);
    }
}
