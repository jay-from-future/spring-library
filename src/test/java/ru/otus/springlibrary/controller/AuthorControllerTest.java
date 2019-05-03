package ru.otus.springlibrary.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.springlibrary.domain.Author;
import ru.otus.springlibrary.service.AuthorService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    private static final String EMPTY = "";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    private Author a1;

    private Author a2;

    @BeforeEach
    void setUp() {
        a1 = new Author("first_name_1", "last_name_1");
        a2 = new Author("first_name_2", "last_name_2");

        given(authorService.findAll()).willReturn(List.of(a1, a2));
        given(authorService.addAuthor("first_name_1", "last_name_1")).willReturn(a1);
    }

    @Test
    void listOfAllAuthors() throws Exception {
        this.mvc.perform(get("/authors"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.allOf(
                        Matchers.containsString(a1.getFirstName()),
                        Matchers.containsString(a1.getLastName()),

                        Matchers.containsString(a2.getFirstName()),
                        Matchers.containsString(a2.getLastName())
                )));
    }

    @Test
    void addAuthor() throws Exception {

        this.mvc.perform(get("/authors/add"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.allOf(
                        Matchers.containsString("Fill author details below"),
                        Matchers.containsString("First name"),
                        Matchers.containsString("Last name")
                )));

        this.mvc.perform(
                post("/authors/add")
                        .param("firstName", a1.getFirstName())
                        .param("lastName", a1.getLastName()))
                .andExpect(status().is(302))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/authors"));
    }

    @Test
    void tryToAddAuthorWithEmptyFirstName() throws Exception {
        this.mvc.perform(
                post("/authors/add")
                        .param("firstName", EMPTY)
                        .param("lastName", a1.getLastName()))
                .andExpect(model().hasErrors());
    }

    @Test
    void tryToAddAuthorWithEmptyLastName() throws Exception {
        this.mvc.perform(
                post("/authors/add")
                        .param("firstName", a1.getFirstName())
                        .param("lastName", EMPTY))
                .andExpect(model().hasErrors());
    }
}