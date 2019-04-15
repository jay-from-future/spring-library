package ru.otus.springlibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.springlibrary.domain.Genre;
import ru.otus.springlibrary.exception.GenreNotFoundException;
import ru.otus.springlibrary.repository.GenreRepository;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public Iterable<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    public Genre addGenre(String genreText) {
        Genre genre = new Genre(genreText);
        return genreRepository.save(genre);
    }

    @Override
    @Transactional
    public void delete(long id) {
        Genre genre = genreRepository.findById(id).orElseThrow(GenreNotFoundException::new);
        genre.getBooks().forEach(b -> b.removeGenre(genre));
        genreRepository.delete(genre);
    }

}
