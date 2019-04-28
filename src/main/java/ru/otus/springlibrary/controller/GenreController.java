package ru.otus.springlibrary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.springlibrary.domain.Genre;
import ru.otus.springlibrary.service.GenreService;

@Controller
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/genres")
    public String listOfAllGenres(Model model) {
        Iterable<Genre> genres = genreService.findAll();
        model.addAttribute("genres", genres);
        return "genres";
    }
}
