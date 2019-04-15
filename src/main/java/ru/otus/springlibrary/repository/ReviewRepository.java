package ru.otus.springlibrary.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.springlibrary.domain.Review;

public interface ReviewRepository extends CrudRepository<Review, Long> {
}
