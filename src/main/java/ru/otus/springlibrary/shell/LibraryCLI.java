package ru.otus.springlibrary.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.BeanListTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.Table;
import org.springframework.shell.table.TableBuilder;
import ru.otus.springlibrary.domain.Author;
import ru.otus.springlibrary.domain.Book;
import ru.otus.springlibrary.service.AuthorService;
import ru.otus.springlibrary.service.BookService;

import java.util.LinkedHashMap;
import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class LibraryCLI {

    private final BookService bookService;

    private final AuthorService authorService;

    @ShellMethod("Show all books")
    public Table showAllBooks() {
        List<Book> allBooks = bookService.getAllBooks();
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("id", "Id");
        headers.put("title", "Title");
        headers.put("author", "Author");
        headers.put("genre", "Genre");
        TableBuilder tableBuilder = new TableBuilder(new BeanListTableModel<>(allBooks, headers));
        tableBuilder.addFullBorder(BorderStyle.oldschool);
        return tableBuilder.build();
    }

    @ShellMethod("Show all authors")
    public Table showAllAuthors() {
        List<Author> allAuthors = authorService.getAllAuthors();
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("id", "Id");
        headers.put("firstName", "First name");
        headers.put("lastName", "Last name");
        TableBuilder tableBuilder = new TableBuilder(new BeanListTableModel<>(allAuthors, headers));
        tableBuilder.addFullBorder(BorderStyle.oldschool);
        return tableBuilder.build();
    }
}
