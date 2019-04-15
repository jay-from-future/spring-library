package ru.otus.springlibrary.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.springlibrary.domain.Book;

public interface BookRepository extends CrudRepository<Book, Long> {
}
