package ru.otus.springlibrary.controller;

import org.bson.types.ObjectId;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ru.otus.springlibrary.domain.Author;
import ru.otus.springlibrary.domain.Book;
import ru.otus.springlibrary.domain.Genre;
import ru.otus.springlibrary.domain.Review;
import ru.otus.springlibrary.service.AuthorService;
import ru.otus.springlibrary.service.BookService;
import ru.otus.springlibrary.service.GenreService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private BookService bookService;

    private Book book;

    @BeforeEach
    void setUp() {
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

        given(authorService.findAll()).willReturn(authors);
        given(genreService.findAll()).willReturn(genres);
        given(bookService.findAll()).willReturn(List.of(book));
    }

    @Test
    void listOfAllBooks() throws Exception {
        this.mvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.allOf(
                        Matchers.containsString(book.getTitle()),
                        Matchers.containsString(book.getAuthors().toString()),
                        Matchers.containsString(book.getGenres().toString())
                )));
    }

    @Test
    void addBook() throws Exception {
        this.mvc.perform(get("/books/add"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.allOf(
                        Matchers.containsString("Fill book details below"),
                        Matchers.containsString("Title"),
                        Matchers.containsString("Author"),
                        Matchers.containsString("Genre")
                )));

        MockHttpServletRequestBuilder addBook = post("/books/add")
                .param("id", book.getId().toString())
                .param("title", book.getTitle())
                .param("authorId", book.getAuthors().get(0).getId().toString())
                .param("genreId", book.getGenres().get(0).getId().toString());

        this.mvc.perform(addBook)
                .andExpect(status().is(302))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/books"));
    }

    @Test
    void tryToAddBookWithEmptyTitle() throws Exception {
        MockHttpServletRequestBuilder addBook = post("/books/add")
                .param("id", book.getId().toString())
                .param("title", "")
                .param("authorId", book.getAuthors().get(0).getId().toString())
                .param("genreId", book.getGenres().get(0).getId().toString());

        this.mvc.perform(addBook)
                .andExpect(model().hasErrors());
    }
}