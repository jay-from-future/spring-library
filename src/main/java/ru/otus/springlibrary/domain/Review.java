package ru.otus.springlibrary.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "REVIEW")
@Data
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_generator")
    @SequenceGenerator(name = "review_generator", sequenceName = "review_sequence", allocationSize = 1)
    @Column(updatable = false)
    private long id;

    @Column(name = "REVIEW_TEXT")
    private String review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOOK_ID", nullable = false)
    private Book book;

    public Review(String review) {
        this.review = review;
    }

    @Override
    public String toString() {
        return id + " " + review;
    }
}
