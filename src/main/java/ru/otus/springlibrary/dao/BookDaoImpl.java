package ru.otus.springlibrary.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.springlibrary.dao.mapper.BookRowMapper;
import ru.otus.springlibrary.domain.Book;
import ru.otus.springlibrary.exception.BookNotFoundException;
import ru.otus.springlibrary.exception.CannotInsertException;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {

    private static final String SELECT_ALL_FROM_BOOK = "select b.id, title, firstname, lastname, genre from book b " +
            "join author a on b.author_fk = a.id join genre g on b.genre_fk = g.id";

    private static final String SELECT_FROM_BOOK_BY_ID = "select b.id, title, firstname, lastname, genre from book b " +
            "left outer join author a on b.author_fk = a.id left outer join genre g on b.genre_fk = g.id where b.id = :id";

    private static final String INSERT_BOOK = "insert into book (title, author_fk, genre_fk) " +
            "values (:title, :authorId, :genreId)";

    private static final String DELETE_FROM_BOOK_BY_ID = "delete from book where id = :id";

    private final GenericDao<Book> genericDao;

    private final BookRowMapper bookRowMapper;

    @Override
    public Book insert(Book book) {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("title", book.getTitle());
        parameters.put("authorId", book.getAuthor().getId());
        parameters.put("genreId", book.getGenre().getId());

        int id = genericDao.insert(book, INSERT_BOOK, parameters);
        Book bookById;
        try {
            bookById = findById(id);
        } catch (BookNotFoundException e) {
            throw new CannotInsertException(String.format("Cannot retrieve book with id = %d after insert", id));
        }
        return bookById;
    }

    @Override
    public Book findById(long id) throws BookNotFoundException {
        Optional<Book> book = genericDao.findById(id, SELECT_FROM_BOOK_BY_ID, bookRowMapper);
        return book.orElseThrow(BookNotFoundException::new);
    }

    @Override
    public Book update(Book book) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return genericDao.delete(id, DELETE_FROM_BOOK_BY_ID);
    }

    @Override
    public List<Book> getAllBooks() {
        return genericDao.getAll(SELECT_ALL_FROM_BOOK, bookRowMapper);
    }
}
