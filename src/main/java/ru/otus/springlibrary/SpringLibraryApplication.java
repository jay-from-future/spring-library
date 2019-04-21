package ru.otus.springlibrary;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.springlibrary.domain.Author;
import ru.otus.springlibrary.domain.Book;
import ru.otus.springlibrary.domain.Genre;
import ru.otus.springlibrary.domain.Review;
import ru.otus.springlibrary.repository.AuthorRepository;
import ru.otus.springlibrary.repository.BookRepository;
import ru.otus.springlibrary.repository.GenreRepository;

import javax.annotation.PostConstruct;
import java.util.Collections;

@SpringBootApplication
public class SpringLibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringLibraryApplication.class, args).close();
    }

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    GenreRepository genreRepository;

    @PostConstruct
    public void fillSomeTestData() {

        // todo rewrite using MongoBee or Mongock

        ObjectId authorId1 = ObjectId.get();
        ObjectId authorId2 = ObjectId.get();

        Author author1 = new Author(authorId1, "test first name 1", "test last name 1");
        Author author2 = new Author(authorId2, "test first name 2", "test last name 2");

        authorRepository.save(author1);
        authorRepository.save(author2);

        ObjectId genreId1 = ObjectId.get();
        ObjectId genreId2 = ObjectId.get();

        Genre genre1 = new Genre(genreId1, "test genre 1");
        Genre genre2 = new Genre(genreId2, "test genre 2");

        genreRepository.save(genre1);
        genreRepository.save(genre2);

        ObjectId bookId = ObjectId.get();

        Review review = new Review(ObjectId.get(), "test review");
        Book book = new Book(bookId, "test book title", Collections.singletonList(author1), Collections.singletonList(genre1),
                Collections.singletonList(review));

        bookRepository.save(book);
    }
}
