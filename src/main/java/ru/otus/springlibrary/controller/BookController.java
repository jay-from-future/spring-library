package ru.otus.springlibrary.controller;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.springlibrary.domain.Book;
import ru.otus.springlibrary.dto.BookDTO;
import ru.otus.springlibrary.service.AuthorService;
import ru.otus.springlibrary.service.BookService;
import ru.otus.springlibrary.service.GenreService;

import javax.validation.Valid;
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
    public String showAddNewBookForm(BookDTO bookDTO, Model model) {
        model.addAttribute("bookDTO", bookDTO);
        // test approach when we have few authors/genres in DB
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());
        return "add_book";
    }


    @PostMapping("/books/add")
    public String addBook(@Valid BookDTO bookDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // test approach when we have few authors/genres in DB
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("genres", genreService.findAll());
            return "add_book";
        }
        // amount of possible authors/genres has been reduced to 1 to simplify UI
        bookService.addBook(bookDTO.getTitle(), List.of(new ObjectId(bookDTO.getAuthorId())),
                List.of(new ObjectId(bookDTO.getGenreId())));
        return "redirect:/books";
    }

    @GetMapping("/books/edit")
    public String showEditBookForm(@RequestParam ObjectId id, Model model) {
        BookDTO bookDTO = mapBookToBookDTO(bookService.findById(id));
        model.addAttribute("bookDTO", bookDTO);
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());
        return "edit_book";
    }

    @PostMapping("/books/edit")
    public String editBook(@RequestParam ObjectId id,
                           @Valid BookDTO bookDTO,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("genres", genreService.findAll());
            return "edit_book";
        }
        List<ObjectId> authorIDs = List.of(new ObjectId(bookDTO.getAuthorId()));
        List<ObjectId> genreIDs = List.of(new ObjectId(bookDTO.getGenreId()));
        bookService.updateBook(id, bookDTO.getTitle(), authorIDs, genreIDs);
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

    private BookDTO mapBookToBookDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId().toString());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthorId(book.getAuthors().get(0).getId().toString());
        bookDTO.setGenreId(book.getGenres().get(0).getId().toString());
        return bookDTO;
    }
}
