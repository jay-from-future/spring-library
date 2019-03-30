package ru.otus.springlibrary.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.springlibrary.domain.Book;
import ru.otus.springlibrary.exception.BookNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {

    private final GenericDao<Book> genericDao;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void insert(Book book) {
        genericDao.insert(book);
    }

    @Override
    public Book findById(long id) throws BookNotFoundException {
        Book book = genericDao.findById(Book.class, id);
        if (book == null) {
            throw new BookNotFoundException();
        }
        return book;
    }

    @Override
    public void update(Book book) {
        genericDao.update(book);
    }

    @Override
    public void delete(Book book) {
        genericDao.delete(book);
    }

    @Override
    public List<Book> getAllBooks() {
        TypedQuery<Book> findAllBooks = em.createNamedQuery("findAllBooks", Book.class);
        return findAllBooks.getResultList();
    }
}
