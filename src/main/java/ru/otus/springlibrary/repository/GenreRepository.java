package ru.otus.springlibrary.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.springlibrary.domain.Genre;

public interface GenreRepository extends CrudRepository<Genre, Long> {
}
