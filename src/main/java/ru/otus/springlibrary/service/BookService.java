package ru.otus.springlibrary.service;

import ru.otus.springlibrary.domain.Book;

import java.util.List;

/**
 * Service to manage Books in spring-library.
 */
public interface BookService {

    /**
     * Returns all books from the database
     *
     * @return list of books
     */
    List<Book> getAllBooks();

    /**
     * Adds new book into library
     *
     * @param title    book title
     * @param authorId book author id
     * @param genreId  book genre id
     * @return true - if book has been added, false - if book already exists
     */
    boolean addBook(String title, int authorId, int genreId);

    /**
     * Deletes book with such id
     *
     * @param id author id
     * @return true - if book has been deleted, false - if book does not exist
     */
    boolean delete(int id);
}
