package ru.otus.springlibrary.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
@RequiredArgsConstructor
public class GenericDaoImpl<T> implements GenericDao<T> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void insert(T t) {
        em.persist(t);
    }

    @Override
    public T findById(Class<T> tClass, long id) {
        return em.find(tClass, id);
    }

    @Override
    public void update(T t) {
        em.merge(t);
    }

    @Override
    public void delete(T t) {
        if (em.contains(t)) {
            // remove managed entity
            em.remove(t);
        } else {
            // remove detached entity
            em.remove(em.merge(t));
        }
    }
}
