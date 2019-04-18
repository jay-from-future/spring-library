package ru.otus.springlibrary.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.springlibrary.domain.Genre;

public interface GenreRepository extends MongoRepository<Genre, ObjectId> {
}
