package ru.otus.springlibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "AUTHOR")
@Data
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

