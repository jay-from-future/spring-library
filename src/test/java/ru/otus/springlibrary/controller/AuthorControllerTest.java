package ru.otus.springlibrary.controller;

import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.otus.springlibrary.domain.Author;
import ru.otus.springlibrary.repository.AuthorRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
class AuthorControllerTest {

    private static final String UPDATED = "UPDATED";

    private MockMvc mockMvc;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private WebApplicationContext context;

    private Author author;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        // clear all and populates with test author
        authorRepository.deleteAll();
        author = new Author("first_name", "last_name");
        authorRepository.save(author);
    }

    @Test
    void listOfAllAuthors() throws Exception {
        mockMvc.perform(get("/authors").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.allOf(
                        Matchers.containsString(author.getFirstName()),
                        Matchers.containsString(author.getLastName()))));
    }

    @Test
    void createUpdateAndDeleteAuthor() throws Exception {
        String testFirstName = "test_first_name";
        String testLastName = "test_last_name";

        // create & read
        MvcResult result = this.mockMvc.perform(
                post("/authors")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format(" { \"firstName\": \"%s\", \"lastName\": \"%s\" }", testFirstName,
                                testLastName)))
                .andExpect(status().isCreated())
                .andExpect(content().string(Matchers.allOf(
                        Matchers.containsString(testFirstName),
                        Matchers.containsString(testLastName)
                )))
                .andReturn();

        JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());
        String selfLink = jsonObject.getJSONObject("_links").getJSONObject("self").getString("href");

        // update
        this.mockMvc.perform(
                patch(selfLink)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format(" { \"firstName\": \"%s\", \"lastName\": \"%s\" }", testFirstName + UPDATED,
                                testLastName))
        )
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.allOf(
                        Matchers.containsString(testFirstName + UPDATED),
                        Matchers.containsString(testLastName)
                )));

        // delete
        this.mockMvc.perform(delete(selfLink)).andExpect(status().isNoContent());
        this.mockMvc.perform(get(selfLink)).andExpect(status().isNotFound());
    }
}