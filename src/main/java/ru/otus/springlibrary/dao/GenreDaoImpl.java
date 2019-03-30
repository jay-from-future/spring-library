package ru.otus.springlibrary.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.springlibrary.domain.Genre;
import ru.otus.springlibrary.exception.GenreNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GenreDaoImpl implements GenreDao {

    private final GenericDao<Genre> genericDao;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void insert(Genre genre) {
        genericDao.insert(genre);
    }

    @Override
    public Genre findById(long id) throws GenreNotFoundException {
        Genre genre = genericDao.findById(Genre.class, id);
        if (genre == null) {
            throw new GenreNotFoundException();
        }
        return genre;
    }

    @Override
    public void update(Genre genre) {
        genericDao.update(genre);
    }

    @Override
    public void delete(Genre genre) {
        genericDao.delete(genre);
    }

    @Override
    public List<Genre> getAllGenres() {
        TypedQuery<Genre> allGenres = em.createNamedQuery("findAllGenres", Genre.class);
        return allGenres.getResultList();
    }
}
