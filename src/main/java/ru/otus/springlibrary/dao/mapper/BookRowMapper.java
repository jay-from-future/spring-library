package ru.otus.springlibrary.dao.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.otus.springlibrary.domain.Author;
import ru.otus.springlibrary.domain.Book;
import ru.otus.springlibrary.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class BookRowMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book;
        int id = rs.getInt("id");
        String title = rs.getString("title");
        String firstname = rs.getString("firstname");
        String lastname = rs.getString("lastname");
        String genre = rs.getString("genre");
        book = new Book(id, title, new Author(firstname, lastname), new Genre(genre));
        return book;
    }
}
