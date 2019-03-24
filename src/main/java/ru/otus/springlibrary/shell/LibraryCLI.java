package ru.otus.springlibrary.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.BeanListTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.Table;
import org.springframework.shell.table.TableBuilder;
import ru.otus.springlibrary.domain.Book;
import ru.otus.springlibrary.service.BookService;

import java.util.LinkedHashMap;
import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class LibraryCLI {

    private final BookService bookService;

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

}
