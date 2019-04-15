package ru.otus.springlibrary.service;

import ru.otus.springlibrary.domain.Book;
import ru.otus.springlibrary.domain.Review;
import ru.otus.springlibrary.exception.BookNotFoundException;

import java.util.List;

/**
 * Service to manage Books in spring-library.
 */
public interface BookService {

    /**
     * Returns all books from the database
     *
     * @return books
     */
    Iterable<Book> findAll();

    /**
     * Adds new book into library
     *
     * @param title     book title
     * @param authorIDs list of book author IDs
     * @param genreIDs  list of book genre IDs
     * @return added book
     */
    Book addBook(String title, List<Long> authorIDs, List<Long> genreIDs);

    /**
     * Deletes book with such id
     *
     * @param id book id
     */
    void delete(long id);

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
     * @return added review
     */
    Review addReview(long id, String reviewText);

    /**
     * Deletes review comment if exists.
     *
     * @param reviewId review id
     */
    void deleteReview(long reviewId);

    /**
     * Updates review comment if exists.
     *
     * @param reviewId          review id
     * @param updatedReviewText updated review text
     */
    void updateReview(long reviewId, String updatedReviewText);
}
