package ru.otus.springlibrary.repository;

import org.bson.types.ObjectId;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import ru.otus.springlibrary.domain.Book;

@CrossOrigin(origins = "*")
public interface BookRepository extends PagingAndSortingRepository<Book, ObjectId> {
}
