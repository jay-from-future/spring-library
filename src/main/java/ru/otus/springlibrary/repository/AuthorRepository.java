package ru.otus.springlibrary.repository;

import org.bson.types.ObjectId;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.otus.springlibrary.domain.Author;

public interface AuthorRepository extends PagingAndSortingRepository<Author, ObjectId> {
}
