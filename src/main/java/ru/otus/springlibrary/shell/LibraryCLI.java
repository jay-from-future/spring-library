package ru.otus.springlibrary.shell;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.table.*;
import ru.otus.springlibrary.domain.Author;
import ru.otus.springlibrary.domain.Book;
import ru.otus.springlibrary.domain.Genre;
import ru.otus.springlibrary.service.AuthorService;
import ru.otus.springlibrary.service.BookService;
import ru.otus.springlibrary.service.GenreService;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static java.lang.String.format;

@ShellComponent
@RequiredArgsConstructor
public class LibraryCLI {

    private static final String TYPE_IS_INCORRECT = "Type '%s' is incorrect! Possible types: %s";

    private static final String HAS_BEEN_SUCCESSFULLY_ADDED = "'%s' has been successfully added.";

    private static final String AUTHOR = "author";

    private static final String BOOK = "book";

    private static final String GENRE = "genre";

    private static final List<String> TYPES = Arrays.asList(AUTHOR, BOOK, GENRE);

    private static final int DEFAULT_WIDTH = 80;

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    @ShellMethod("Show book reviews")
    public Table showBookReviews(@ShellOption ObjectId bookId) {
        Book book = bookService.findById(bookId);
        System.out.println(String.format("%s | %s | %s | %s", book.getId(), book.getTitle(), book.getAuthors(),
                book.getGenres()));
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("id", "Id");
        headers.put("review", "Review");
        BeanListTableModel model = new BeanListTableModel<>(book.getReviews(), headers);
        return wrapInTableWithReviews(model);
    }

    @ShellMethod("Add review to book")
    public void addReview(@ShellOption ObjectId bookId,
                          @ShellOption String review) {
        if (isReviewTooLong(review)) {
            return;
        }
        bookService.addReview(bookId, review);
    }

    @ShellMethod("Remove book review")
    public void removeReview(@ShellOption ObjectId bookId,
                             @ShellOption ObjectId reviewId) {
        bookService.deleteReview(bookId, reviewId);
    }

    @ShellMethod("Update book review")
    public void updateReview(@ShellOption ObjectId bookId,
                             @ShellOption ObjectId reviewId,
                             @ShellOption String updatedReview) {
        if (isReviewTooLong(updatedReview)) {
            return;
        }
        bookService.updateReview(bookId, reviewId, updatedReview);
    }

    @ShellMethod("Show all books")
    public String showAllBooks() {
        Iterable<Book> allBooks = bookService.findAll();
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("id", "Id");
        headers.put("title", "Title");
        headers.put("authors", "Authors");
        headers.put("genres", "Genres");
        BeanListTableModel model = new BeanListTableModel<>(allBooks, headers);
        // it is needed to render table here, otherwise you'll get LazyInitializationException
        return wrapInTable(model).render(DEFAULT_WIDTH);
    }

    @ShellMethod("Show all authors")
    public Table showAllAuthors() {
        Iterable<Author> allAuthors = authorService.findAll();
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("id", "Id");
        headers.put("firstName", "First name");
        headers.put("lastName", "Last name");
        BeanListTableModel model = new BeanListTableModel<>(allAuthors, headers);
        return wrapInTable(model);
    }

    @ShellMethod("Show all genres")
    public Table showAllGenres() {
        Iterable<Genre> allGenres = genreService.findAll();
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("id", "Id");
        headers.put("genre", "Genre");
        BeanListTableModel model = new BeanListTableModel<>(allGenres, headers);
        return wrapInTable(model);
    }

    @ShellMethod("Add new author")
    public void addAuthor(@ShellOption String firstName,
                          @ShellOption String lastName) {
        printResult(authorService.addAuthor(firstName, lastName).toString());
    }

    @ShellMethod("Add new book")
    public void addBook(@ShellOption String title,
                        @ShellOption List<ObjectId> authorIDs,
                        @ShellOption List<ObjectId> genreIDs) {
        printResult(bookService.addBook(title, authorIDs, genreIDs).toString());
    }

    @ShellMethod("Add new genre")
    public void addGenre(@ShellOption String genre) {
        printResult(genreService.addGenre(genre).toString());
    }

    @ShellMethod("Remove item")
    public void removeItem(@ShellOption String type,
                           @ShellOption ObjectId id) {
        // check that type exists
        if (TYPES.stream().noneMatch(t -> t.equals(type))) {
            System.err.println(format(TYPE_IS_INCORRECT, type, Arrays.toString(TYPES.toArray())));
        }
        switch (type) {
            case AUTHOR:
                authorService.delete(id);
                break;
            case BOOK:
                bookService.delete(id);
                break;
            case GENRE:
                genreService.delete(id);
                break;
            default:
                throw new IllegalArgumentException(type + " is unsupported!");
        }
    }

    private Table wrapInTable(BeanListTableModel model) {
        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.addFullBorder(BorderStyle.oldschool);
        return tableBuilder.build();
    }

    private Table wrapInTableWithReviews(TableModel model) {
        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.on(CellMatchers.column(1)).addSizer(new AbsoluteWidthSizeConstraints(40));
        tableBuilder.addFullBorder(BorderStyle.oldschool);
        return tableBuilder.build();
    }

    private void printResult(String item) {
        System.out.println(format(HAS_BEEN_SUCCESSFULLY_ADDED, item));
    }

    private boolean isReviewTooLong(String newReview) {
        if (newReview.length() > 255) {
            System.err.println("Review text is longer than 255 symbols which is maximum allowed!");
            return true;
        }
        return false;
    }
}
