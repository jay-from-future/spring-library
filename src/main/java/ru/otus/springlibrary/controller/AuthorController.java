package ru.otus.springlibrary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.springlibrary.domain.Author;
import ru.otus.springlibrary.service.AuthorService;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/authors")
    public String listOfAllAuthors(Model model) {
        Iterable<Author> authors = authorService.findAll();
        model.addAttribute("authors", authors);
        return "authors";
    }
}
