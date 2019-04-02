package ru.otus.springlibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "AUTHOR")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name = "findAllAuthors", query = "select a from Author a")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_generator")
    @SequenceGenerator(name = "author_generator", sequenceName = "author_sequence", allocationSize = 1)
    @Column(updatable = false)
    private long id;

    @Column(name = "FIRSTNAME")
    private String firstName;

    @Column(name = "LASTNAME")
    private String lastName;

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Author(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
