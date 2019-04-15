package ru.otus.springlibrary.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.table.*;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.springlibrary.domain.Author;
import ru.otus.springlibrary.domain.Book;
import ru.otus.springlibrary.domain.Genre;
import ru.otus.springlibrary.exception.BookNotFoundException;
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

    private static final String ALREADY_EXISTS = "%s already exists!";

    private static final String HAS_BEEN_SUCCESSFULLY_ADDED = "%s has been successfully added.";

    private static final String AUTHOR = "author";

    private static final String BOOK = "book";

    private static final String GENRE = "genre";

    private static final List<String> TYPES = Arrays.asList(AUTHOR, BOOK, GENRE);

    private static final String CANNOT_REMOVE_ITEM_DOES_NOT_EXIST = "Cannot remove %s with id = %d. Item does not exist " +
            "or has a reference from other item!";

    private static final String SUCCESSFULLY_DELETED = "%s item with id = %d successfully deleted";

    private static final int DEFAULT_WIDTH = 80;

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    @ShellMethod("Show book reviews")
    @Transactional
    public Table showBookReviews(@ShellOption long bookId) {
        try {
            Book book = bookService.findById(bookId);
            System.out.println(String.format("%d | %s | %s | %s", book.getId(), book.getTitle(), book.getAuthors(),
                    book.getGenres()));
            LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
            headers.put("id", "Id");
            headers.put("review", "Review");
            BeanListTableModel model = new BeanListTableModel<>(book.getReviews(), headers);
            return wrapInTableWithReviews(model);
        } catch (BookNotFoundException e) {
            System.err.println(String.format("Book with id = %d does not exist!", bookId));
            return null;
        }
    }

    @ShellMethod("Add review to book")
    public void addReview(@ShellOption long bookId,
                          @ShellOption String review) {
        if (checkReviewLength(review)) {
            return;
        }
        boolean result = bookService.addReview(bookId, review);
        if (result) {
            System.out.println("Review comment has been successfully added");
        } else {
            System.err.println("Review comment cannot be added");
        }
    }

    @ShellMethod("Remove book review")
    public void removeReview(@ShellOption long reviewId) {
        boolean result = bookService.deleteReview(reviewId);
        if (result) {
            System.out.println("Review comment has been successfully removed");
        } else {
            System.err.println("Review comment cannot be removed");
        }
    }

    @ShellMethod("Update book review")
    public void updateReview(@ShellOption long reviewId,
                             @ShellOption String updatedReview) {
        if (checkReviewLength(updatedReview)) {
            return;
        }
        boolean result = bookService.updateReview(reviewId, updatedReview);
        if (result) {
            System.out.println("Review comment has been successfully updated");
        } else {
            System.err.println("Review comment cannot be updated");
        }
    }

    @ShellMethod("Show all books")
    @Transactional
    public String showAllBooks() {
        List<Book> allBooks = bookService.getAllBooks();
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
        printResult(authorService.addAuthor(firstName, lastName), AUTHOR);
    }

    @ShellMethod("Add new book")
    public void addBook(@ShellOption String title,
                        @ShellOption List<Long> authorIDs,
                        @ShellOption List<Long> genreIDs) {
        printResult(bookService.addBook(title, authorIDs, genreIDs), BOOK);
    }

    @ShellMethod("Add new genre")
    public void addGenre(@ShellOption String genre) {
        printResult(genreService.addGenre(genre), GENRE);
    }

    @ShellMethod("Remove item")
    public void removeItem(@ShellOption String type,
                           @ShellOption long id) {
        // check that type exists
        if (TYPES.stream().noneMatch(t -> t.equals(type))) {
            System.err.println(format(TYPE_IS_INCORRECT, type, Arrays.toString(TYPES.toArray())));
        }
        boolean result;
        switch (type) {
            case AUTHOR:
                result = authorService.delete(id);
                break;
            case BOOK:
                result = bookService.delete(id);
                break;
            case GENRE:
                result = genreService.delete(id);
                break;
            default:
                throw new IllegalArgumentException(type + " is unsupported!");
        }
        if (result) {
            System.out.println(format(SUCCESSFULLY_DELETED, type, id));
        } else {
            System.err.println(format(CANNOT_REMOVE_ITEM_DOES_NOT_EXIST, type, id));
        }
    }

    private Table wrapInTable(BeanListTableModel model) {
        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.addFullBorder(BorderStyle.oldschool);
        return tableBuilder.build();
    }

    private Table wrapInTableWithReviews(BeanListTableModel model) {
        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.on(CellMatchers.column(1)).addSizer(new AbsoluteWidthSizeConstraints(40));
        tableBuilder.addFullBorder(BorderStyle.oldschool);
        return tableBuilder.build();
    }

    private void printResult(boolean result, String type) {
        if (result) {
            System.out.println(format(HAS_BEEN_SUCCESSFULLY_ADDED, type));
        } else {
            System.err.println(format(ALREADY_EXISTS, type));
        }
    }

    private boolean checkReviewLength(@ShellOption String newReview) {
        if (newReview.length() > 255) {
            System.err.println("Review text is longer than 255 symbols which is maximum allowed!");
            return true;
        }
        return false;
    }
}
