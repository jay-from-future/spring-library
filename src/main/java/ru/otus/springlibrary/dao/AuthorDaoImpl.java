package ru.otus.springlibrary.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.springlibrary.domain.Author;
import ru.otus.springlibrary.exception.AuthorNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AuthorDaoImpl implements AuthorDao {

    private final GenericDao<Author> genericDao;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void insert(Author author) {
        genericDao.insert(author);
    }

    @Override
    public Author findById(long id) throws AuthorNotFoundException {
        Author author = genericDao.findById(Author.class, id);
        if (author == null) {
            throw new AuthorNotFoundException();
        }
        return author;
    }

    @Override
    public void update(Author author) {
        genericDao.update(author);
    }

    @Override
    public void delete(Author author) {
        genericDao.delete(author);
    }

    @Override
    public List<Author> getAllAuthors() {
        TypedQuery<Author> selectAllAuthors = em.createNamedQuery("findAllAuthors", Author.class);
        return selectAllAuthors.getResultList();
    }
}
