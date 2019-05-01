package ru.otus.springlibrary.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import ru.otus.springlibrary.domain.Author;
import ru.otus.springlibrary.domain.Book;
import ru.otus.springlibrary.domain.Genre;
import ru.otus.springlibrary.domain.Review;
import ru.otus.springlibrary.exception.BookNotFoundException;
import ru.otus.springlibrary.exception.ReviewNotFoundException;
import ru.otus.springlibrary.repository.AuthorRepository;
import ru.otus.springlibrary.repository.BookRepository;
import ru.otus.springlibrary.repository.GenreRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    @Override
    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book addBook(String title, List<ObjectId> authorIDs, List<ObjectId> genreIDs) {
        List<Author> authors = authorIDs.stream()
                .map(id -> authorRepository.findById(id).orElseThrow())
                .collect(Collectors.toList());

        List<Genre> genres = genreIDs.stream()
                .map(id -> genreRepository.findById(id).orElseThrow())
                .collect(Collectors.toList());

        Book book = new Book(title, authors, genres);
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(ObjectId id, String title, List<ObjectId> authorIDs, List<ObjectId> genreIDs) {
        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);

        // todo check which fields should be update beforehand to avoid useless fields update

        book.setTitle(title);

        List<Author> authors = authorIDs.stream()
                .map(authorID -> authorRepository.findById(authorID).orElseThrow())
                .collect(Collectors.toList());

        book.setAuthors(authors);

        List<Genre> genres = genreIDs.stream()
                .map(genreId -> genreRepository.findById(genreId).orElseThrow())
                .collect(Collectors.toList());

        book.setGenres(genres);

        return bookRepository.save(book);
    }

    @Override
    public void delete(ObjectId id) {
        // todo Authors and Genres without book will not be removed, but it is an open question when clean up them
        // todo consider using Spring Batch later to clean up database
        Book book = findBookById(id);
        bookRepository.delete(book);
    }

    @Override
    public Book findById(ObjectId id) {
        return bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
    }

    @Override
    public ObjectId addReview(ObjectId bookId, String review) {
        Book book = findBookById(bookId);
        ObjectId reviewId = ObjectId.get();
        book.getReviews().add(new Review(reviewId, review));
        bookRepository.save(book);
        return reviewId;
    }

    @Override
    public void deleteReview(ObjectId bookId, ObjectId reviewId) {
        Book book = findBookById(bookId);
        List<Review> reviews = book.getReviews();
        Review review = getReview(reviewId, reviews);
        reviews.remove(review);
        book.setReviews(reviews);
        bookRepository.save(book);
    }

    @Override
    public void updateReview(ObjectId bookId, ObjectId reviewId, String updatedReviewText) {
        Book book = findBookById(bookId);
        Review review = getReview(reviewId, book.getReviews());
        review.setReview(updatedReviewText);
        bookRepository.save(book);
    }

    private Review getReview(ObjectId reviewId, List<Review> reviews) {
        return reviews.stream()
                .filter(r -> r.getId().equals(reviewId))
                .findFirst()
                .orElseThrow(ReviewNotFoundException::new);
    }

    private Book findBookById(ObjectId bookId) {
        return bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
    }

}
