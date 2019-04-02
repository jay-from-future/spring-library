package ru.otus.springlibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "BOOK")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name = "findAllBooks", query = "select b from Book b")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_generator")
    @SequenceGenerator(name = "book_generator", sequenceName = "book_sequence", allocationSize = 1)
    @Column(updatable = false)
    private long id;

    @Column(name = "TITLE")
    private String title;

    @OneToOne
    private Author author;

    @OneToOne
    private Genre genre;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book", cascade = CascadeType.ALL)
    private List<Review> reviews;

    public Book(String title, Author author, Genre genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public Book(String title) {
        this.title = title;
    }

    public void addReview(Review review) {
        reviews.add(review);
        review.setBook(this);
    }

    public void deleteReview(Review review) {
        reviews.remove(review);
        review.setBook(null);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", genre=" + genre +
                '}';
    }
}
