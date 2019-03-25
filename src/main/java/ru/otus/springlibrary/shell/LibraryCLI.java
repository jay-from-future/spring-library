package ru.otus.springlibrary.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.table.BeanListTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.Table;
import org.springframework.shell.table.TableBuilder;
import ru.otus.springlibrary.domain.Author;
import ru.otus.springlibrary.domain.Book;
import ru.otus.springlibrary.domain.Genre;
import ru.otus.springlibrary.service.AuthorService;
import ru.otus.springlibrary.service.BookService;
import ru.otus.springlibrary.service.GenreService;

import java.util.LinkedHashMap;
import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class LibraryCLI {

    private static final String AUTHOR_HAS_BEEN_SUCCESSFULLY_ADDED = "Author has been successfully added.";

    private static final String AUTHOR_ALREADY_EXISTS = "Cannot add author with firstName = %s, lastName = %s." +
            " Author already exists!";

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    @ShellMethod("Show all books")
    public Table showAllBooks() {
        List<Book> allBooks = bookService.getAllBooks();
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("id", "Id");
        headers.put("title", "Title");
        headers.put("author", "Author");
        headers.put("genre", "Genre");
        BeanListTableModel model = new BeanListTableModel<>(allBooks, headers);
        return wrapInTable(model);
    }

    @ShellMethod("Show all authors")
    public Table showAllAuthors() {
        List<Author> allAuthors = authorService.getAllAuthors();
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("id", "Id");
        headers.put("firstName", "First name");
        headers.put("lastName", "Last name");
        BeanListTableModel model = new BeanListTableModel<>(allAuthors, headers);
        return wrapInTable(model);
    }

    @ShellMethod("Show all genres")
    public Table showAllGenres() {
        List<Genre> allGenres = genreService.getAllGenres();
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("id", "Id");
        headers.put("genre", "Genre");
        BeanListTableModel model = new BeanListTableModel<>(allGenres, headers);
        return wrapInTable(model);
    }

    @ShellMethod("Add new author")
    public void addAuthor(@ShellOption String firstName,
                          @ShellOption String lastName) {
        if (authorService.addAuthor(firstName, lastName)) {
            System.out.println(AUTHOR_HAS_BEEN_SUCCESSFULLY_ADDED);
        } else {
            System.err.println(String.format(AUTHOR_ALREADY_EXISTS, firstName, lastName));
        }
    }

    private Table wrapInTable(BeanListTableModel model) {
        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.addFullBorder(BorderStyle.oldschool);
        return tableBuilder.build();
    }
}
