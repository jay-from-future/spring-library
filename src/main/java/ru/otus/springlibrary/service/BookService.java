package ru.otus.springlibrary.service;

import ru.otus.springlibrary.domain.Book;
import ru.otus.springlibrary.exception.BookNotFoundException;

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
    boolean addBook(String title, long authorId, long genreId);

    /**
     * Deletes book with such id
     *
     * @param id book id
     * @return true - if book has been deleted, false - if book does not exist
     */
    boolean delete(long id);

    /**
     * Finds book by id.
     *
     * @param id book id
     * @return {@link Book} if book with such id exists
     * @throws BookNotFoundException if book with such id does not exist
     */
    Book findById(long id) throws BookNotFoundException;

    /**
     * Adds new review comment to the related book.
     *
     * @param id         book id
     * @param reviewText review text
     * @return true if review comment has been successfully added, otherwise - false.
     */
    boolean addReview(long id, String reviewText);

    /**
     * Deletes review comment if exists.
     *
     * @param reviewId review id
     * @return true if review comment has been successfully removed, otherwise - false.
     */
    boolean deleteReview(long reviewId);

    /**
     * Updates review comment if exists.
     *
     * @param reviewId          review id
     * @param updatedReviewText updated review text
     * @return true if review comment has been successfully updated, otherwise - false.
     */
    boolean updateReview(long reviewId, String updatedReviewText);
}
