package ru.otus.springlibrary.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "GENRE")
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

    @Column(name = "GENRE")
    private String genre;

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
}
