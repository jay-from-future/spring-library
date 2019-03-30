package ru.otus.springlibrary.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "BOOK")
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name = "findAllBooks", query = "select b from Book b")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_generator")
    @SequenceGenerator(name = "book_generator", sequenceName = "book_sequence")
    @Column(updatable = false)
    private long id;

    @Column(name = "TITLE")
    private String title;

    @OneToOne
    private Author author;

    @OneToOne
    private Genre genre;

    public Book(String title, Author author, Genre genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public Book(String title) {
        this.title = title;
    }
}
