package ru.otus.springlibrary.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.otus.springlibrary.domain.Genre;
import ru.otus.springlibrary.repository.GenreRepository;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
class GenreControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private GenreRepository genreRepository;

    private Genre g1;

    private Genre g2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        // clear all and populates with test author
        g1 = new Genre("genre 1");
        g2 = new Genre("genre 2");

        genreRepository.deleteAll();
        genreRepository.saveAll(List.of(g1, g2));
    }

    @Test
    void listOfAllGenres() throws Exception {
        this.mockMvc.perform(get("/genres"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.allOf(
                        Matchers.containsString(g1.getGenre()),
                        Matchers.containsString(g2.getGenre())
                )));

    }
}