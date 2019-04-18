package ru.otus.springlibrary.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import ru.otus.springlibrary.domain.Author;
import ru.otus.springlibrary.domain.Book;
import ru.otus.springlibrary.domain.Genre;
import ru.otus.springlibrary.exception.BookNotFoundException;
import ru.otus.springlibrary.repository.AuthorRepository;
import ru.otus.springlibrary.repository.BookRepository;
import ru.otus.springlibrary.repository.GenreRepository;

import java.util.Arrays;
import java.util.List;

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

        // todo draft solution
        
        ObjectId aId = authorIDs.get(0);
        Author author = authorRepository.findById(aId).orElseThrow();

        ObjectId gId = genreIDs.get(0);
        Genre genre = genreRepository.findById(gId).orElseThrow();

        Book book = new Book(title, Arrays.asList(author), Arrays.asList(genre));
        return bookRepository.save(book);
    }

    @Override
    public void delete(ObjectId id) {
        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        bookRepository.delete(book);

//        List<Author> authors = book.getAuthors();
//        for (Author a : authors) {
//            List<Book> books = a.getBooks();
//            if (books.contains(book) && books.size() == 1) {
//                authorRepository.delete(a);
//            }
//        }
//
//        List<Genre> genres = book.getGenres();
//        for (Genre g : genres) {
//            List<Book> books = g.getBooks();
//            if (books.contains(book) && books.size() == 1) {
//                genreRepository.delete(g);
//            }
//        }
    }

    @Override
    public Book findById(ObjectId id) {
        return bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
    }

    @Override
    public void addReview(ObjectId bookId, String review) {
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        book.getReviews().add(review);
        bookRepository.save(book);
    }

    @Override
    public void deleteReview(ObjectId reviewId) {
//        List<Book> all = bookRepository.findAll();
//        Review review = all.stream().flatMap(b -> b.getReviews().stream()).filter(r -> r.getId().equals(reviewId)).findFirst().orElseThrow(ReviewNotFoundException::new);
//        review.setReview("deleted");
    }

    @Override
    public void updateReview(ObjectId reviewId, String updatedReviewText) {
//        Review review = bookRepository.findAll().stream().flatMap(b -> b.getReviews().stream()).filter(r -> r.getId().equals(reviewId)).findFirst().orElseThrow(ReviewNotFoundException::new);
//        review.setReview(updatedReviewText);
//bookRepository.save()
    }
}
