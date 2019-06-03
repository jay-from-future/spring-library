package ru.otus.springlibrary.controller;

import org.bson.types.ObjectId;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.otus.springlibrary.domain.Author;
import ru.otus.springlibrary.domain.Book;
import ru.otus.springlibrary.domain.Genre;
import ru.otus.springlibrary.domain.Review;
import ru.otus.springlibrary.repository.AuthorRepository;
import ru.otus.springlibrary.repository.BookRepository;
import ru.otus.springlibrary.repository.GenreRepository;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
class BookControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private WebApplicationContext context;

    private Book book;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        // clear all and populates with test author
        Author a1 = new Author(ObjectId.get(), "first_name_1", "last_name_1");
        Author a2 = new Author(ObjectId.get(), "first_name_2", "last_name_2");

        Genre g1 = new Genre(ObjectId.get(), "genre 1");
        Genre g2 = new Genre(ObjectId.get(), "genre 2");

        Review r1 = new Review(ObjectId.get(), "review 1");
        Review r2 = new Review(ObjectId.get(), "review 2");

        List<Author> authors = List.of(a1, a2);
        List<Genre> genres = List.of(g1, g2);
        List<Review> reviews = List.of(r1, r2);

        book = new Book(ObjectId.get(), "title 1", authors, genres, reviews);

        authorRepository.deleteAll();
        genreRepository.deleteAll();
        bookRepository.deleteAll();

        authorRepository.saveAll(authors);
        genreRepository.saveAll(genres);
        bookRepository.save(book);
    }

    @Test
    void listOfAllBooks() throws Exception {
        this.mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString(book.getTitle())));
    }
}