package ru.otus.springlibrary.dao.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.otus.springlibrary.dao.AuthorDao;
import ru.otus.springlibrary.dao.GenreDao;
import ru.otus.springlibrary.exception.AuthorNotFoundException;
import ru.otus.springlibrary.exception.GenreNotFoundException;
import ru.otus.springlibrary.exception.InvalidBookReferences;
import ru.otus.springlibrary.domain.Author;
import ru.otus.springlibrary.domain.Book;
import ru.otus.springlibrary.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class BookRowMapper implements RowMapper<Book> {

    private final AuthorDao authorDao;

    private final GenreDao genreDao;

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book;
        int id = rs.getInt("id");
        String title = rs.getString("title");
        try {
            Author author = authorDao.findById(rs.getInt("author_fk"));
            Genre genre = genreDao.findById(rs.getInt("genre_fk"));
            book = new Book(id, title, author, genre);
        } catch (AuthorNotFoundException | GenreNotFoundException e) {
            throw new InvalidBookReferences("Cannot map row to Book entity due to incorrect reference.", e);
        }
        return book;
    }
}
