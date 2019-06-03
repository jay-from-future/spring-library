package ru.otus.springlibrary.repository;

import org.bson.types.ObjectId;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.otus.springlibrary.domain.Book;

public interface BookRepository extends PagingAndSortingRepository<Book, ObjectId> {
}
