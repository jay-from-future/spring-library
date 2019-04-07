package ru.otus.springlibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "genre")
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name = "findAllGenres", query = "select g from Genre g")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genre_generator")
    @SequenceGenerator(name = "genre_generator", sequenceName = "genre_sequence", allocationSize = 1)
    @Column(updatable = false)
    private long id;

    @Column(name = "genre")
    private String genre;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "map_book_genre",
            joinColumns = {@JoinColumn(name = "genre_id")},
            inverseJoinColumns = {@JoinColumn(name = "book_id")})
    private List<Book> books;

    public Genre(String genre) {
        this.genre = genre;
    }

    public Genre(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return genre;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }
}
