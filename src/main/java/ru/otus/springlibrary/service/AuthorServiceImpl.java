package ru.otus.springlibrary.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import ru.otus.springlibrary.domain.Author;
import ru.otus.springlibrary.exception.AuthorNotFoundException;
import ru.otus.springlibrary.repository.AuthorRepository;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public Iterable<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author addAuthor(String firstName, String lastName) {
        Author author = new Author(firstName, lastName);
        return authorRepository.save(author);
    }

    @Override
    public void delete(ObjectId id) {
        Author author = authorRepository.findById(id).orElseThrow(AuthorNotFoundException::new);
        authorRepository.delete(author);
    }
}
