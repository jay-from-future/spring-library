package ru.otus.springlibrary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.springlibrary.domain.Author;
import ru.otus.springlibrary.dto.AuthorDTO;
import ru.otus.springlibrary.service.AuthorService;

import javax.validation.Valid;

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

    @GetMapping("/authors/add")
    public String showAddNewAuthorForm(AuthorDTO authorDTO, Model model) {
        model.addAttribute("authorDTO", authorDTO);
        return "add_author";
    }

    @PostMapping("/authors/add")
    public String addAuthor(@Valid AuthorDTO authorDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add_author";
        }
        authorService.addAuthor(authorDTO.getFirstName(), authorDTO.getLastName());
        return "redirect:/authors";
    }
}
