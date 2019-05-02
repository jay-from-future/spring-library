package ru.otus.springlibrary.controller;

import org.bson.types.ObjectId;
import org.hamcrest.Matchers;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    @Test
    void listOfAllAuthors() throws Exception {
        Author a1 = new Author("first_name_1", "last_name_1");
        Author a2 = new Author("first_name_2", "last_name_2");

        given(authorService.findAll()).willReturn(List.of(a1, a2));

        this.mvc.perform(get("/authors"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.allOf(
                        Matchers.containsString(a1.getFirstName()),
                        Matchers.containsString(a1.getLastName()),

                        Matchers.containsString(a2.getFirstName()),
                        Matchers.containsString(a2.getLastName())
                )));
    }
}