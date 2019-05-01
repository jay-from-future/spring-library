package ru.otus.springlibrary.controller;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.springlibrary.domain.Book;
import ru.otus.springlibrary.service.AuthorService;
import ru.otus.springlibrary.service.BookService;
import ru.otus.springlibrary.service.GenreService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    @GetMapping("/books")
    public String listOfAllBooks(Model model) {
        Iterable<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/books/add")
    public String showAddNewBookForm(Model model) {
        // todo test approach when we have few authors/genres in DB
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());
        return "add_book";
    }


    @PostMapping("/books/add")
    public String addNewBook(@RequestParam String title,
                             @RequestParam ObjectId authorID,
                             @RequestParam ObjectId genreID) {
        // todo amount of possible authors/genres has been reduced to 1 to simplify UI
        bookService.addBook(title, List.of(authorID), List.of(genreID));
        return "redirect:/books";
    }

    @GetMapping("/books/remove")
    public String showRemoveBookForm(@RequestParam ObjectId id,
                                     Model model) {
        Book book = bookService.findById(id);
        model.addAttribute("book", book);
        return "remove_book";
    }

    @DeleteMapping("/books/remove")
    public String removeBook(@RequestParam ObjectId id) {
        bookService.delete(id);
        return "redirect:/books";
    }
}
