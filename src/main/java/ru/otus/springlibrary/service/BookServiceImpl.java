package ru.otus.springlibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.springlibrary.domain.Author;
import ru.otus.springlibrary.domain.Book;
import ru.otus.springlibrary.domain.Genre;
import ru.otus.springlibrary.domain.Review;
import ru.otus.springlibrary.exception.BookNotFoundException;
import ru.otus.springlibrary.exception.ReviewNotFoundException;
import ru.otus.springlibrary.repository.AuthorRepository;
import ru.otus.springlibrary.repository.BookRepository;
import ru.otus.springlibrary.repository.GenreRepository;
import ru.otus.springlibrary.repository.ReviewRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final ReviewRepository reviewRepository;

    @Override
    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public Book addBook(String title, List<Long> authorIDs, List<Long> genreIDs) {
        Book book = new Book(title);

        List<Author> authors = convertIDsToEntities(authorIDs, authorRepository);
        authors.forEach(book::addAuthor);

        List<Genre> genres = convertIDsToEntities(genreIDs, genreRepository);
        genres.forEach(book::addGenre);

        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public void delete(long id) {
        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        bookRepository.delete(book);

        List<Author> authors = book.getAuthors();
        for (Author a : authors) {
            List<Book> books = a.getBooks();
            if (books.contains(book) && books.size() == 1) {
                authorRepository.delete(a);
            }
        }

        List<Genre> genres = book.getGenres();
        for (Genre g : genres) {
            List<Book> books = g.getBooks();
            if (books.contains(book) && books.size() == 1) {
                genreRepository.delete(g);
            }
        }
    }

    @Override
    public Book findById(long id) {
        return bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
    }

    @Override
    @Transactional
    public Review addReview(long id, String reviewText) {
        Review review = new Review(reviewText);
        Review savedReview = reviewRepository.save(review);

        Book book = findById(id);
        book.addReview(review);
        bookRepository.save(book);

        return savedReview;
    }

    @Override
    public void deleteReview(long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        reviewRepository.delete(review);
    }

    @Override
    public void updateReview(long reviewId, String updatedReviewText) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        review.setReview(updatedReviewText);
        reviewRepository.save(review);
    }

    private <T> List<T> convertIDsToEntities(List<Long> ids, CrudRepository<T, Long> repository) {
        return ids.stream()
                .filter(repository::existsById)
                .map(id -> repository.findById(id).orElseThrow())
                .collect(Collectors.toList());
    }
}
