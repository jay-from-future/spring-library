package ru.otus.springlibrary.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.otus.springlibrary.domain.Book;

import javax.annotation.PostConstruct;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {

    private static final String SELECT_ALL_FROM_BOOK = "select * from book";

    private static final String SELECT_FROM_BOOK_BY_ID = "select * from book where id = :id";

    private final AuthorDao authorDao;

    private final GenreDao genreDao;

    private final GenericDao<Book> genericDao;

    private RowMapper<Book> bookRowMapper;

    @PostConstruct
    void postConstruct() {
        bookRowMapper = (rs, rowNum) -> new Book(
                rs.getInt("id"),
                rs.getString("title"),
                authorDao.findById(rs.getInt("author_id")),
                genreDao.findById(rs.getInt("genre_id"))
        );
    }


    @Override
    public Book insert(Book book) {
        return null;
    }

    @Override
    public Book findById(int id) {
        return genericDao.findById(id, SELECT_FROM_BOOK_BY_ID, bookRowMapper);
    }

    @Override
    public Book update(Book book) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<Book> getAllBooks() {
        return genericDao.getAll(SELECT_ALL_FROM_BOOK, bookRowMapper);
    }
}
