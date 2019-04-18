package ru.otus.springlibrary.service;

import org.bson.types.ObjectId;
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
    Book addBook(String title, List<ObjectId> authorIDs, List<ObjectId> genreIDs);

    /**
     * Deletes book with such id
     *
     * @param id book id
     */
    void delete(ObjectId id);

    /**
     * Finds book by id.
     *
     * @param id book id
     * @return {@link Book} if book with such id exists
     * @throws BookNotFoundException if book with such id does not exist
     */
    Book findById(ObjectId id) throws BookNotFoundException;

    /**
     * Adds new review comment to the related book.
     *
     * @param bookId         book id
     * @param reviewText review text
     */
    void addReview(ObjectId bookId, String reviewText);

    /**
     * Deletes review comment if exists.
     *
     * @param reviewId review id
     */
    void deleteReview(ObjectId reviewId);

    /**
     * Updates review comment if exists.
     *
     * @param reviewId          review id
     * @param updatedReviewText updated review text
     */
    void updateReview(ObjectId reviewId, String updatedReviewText);
}
