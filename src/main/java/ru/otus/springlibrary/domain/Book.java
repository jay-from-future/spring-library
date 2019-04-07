package ru.otus.springlibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name = "findAllBooks", query = "select b from Book b")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_generator")
    @SequenceGenerator(name = "book_generator", sequenceName = "book_sequence", allocationSize = 1)
    @Column(updatable = false)
    private long id;

    @Column(name = "title")
    private String title;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "map_book_author",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "author_id")})
    private List<Author> authors;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "map_book_genre",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")})
    private List<Genre> genres;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book", cascade = CascadeType.ALL)
    private List<Review> reviews;

    public Book(String title) {
        this.title = title;
    }

    public void addAuthor(Author author) {
        authors.add(author);
        author.addBook(this);
    }

    public void removeAuthor(Author author) {
        authors.remove(author);
        author.removeBook(this);
    }

    public void addGenre(Genre genre) {
        genres.add(genre);
        genre.addBook(this);
    }

    public void removeGenre(Genre genre) {
        genres.remove(genre);
        genre.removeBook(this);
    }

    public void addReview(Review review) {
        reviews.add(review);
        review.setBook(this);
    }

    public void deleteReview(Review review) {
        reviews.remove(review);
        review.setBook(null);
    }
}
