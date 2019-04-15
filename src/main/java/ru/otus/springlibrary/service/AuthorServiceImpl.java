package ru.otus.springlibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public void delete(long id) {
        Author author = authorRepository.findById(id).orElseThrow(AuthorNotFoundException::new);
        author.getBooks().forEach(b -> b.removeAuthor(author));
        authorRepository.delete(author);
    }
}
