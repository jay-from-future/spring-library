package ru.otus.springlibrary.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.springlibrary.domain.Author;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
