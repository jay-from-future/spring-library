package ru.otus.springlibrary.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.springlibrary.domain.Review;
import ru.otus.springlibrary.exception.ReviewNotFoundException;

@Repository
@RequiredArgsConstructor
public class ReviewDaoImpl implements ReviewDao {

    private final GenericDao<Review> genericDao;

    @Override
    public void insert(Review review) {
        genericDao.insert(review);
    }

    @Override
    public Review findById(long id) throws ReviewNotFoundException {
        Review review = genericDao.findById(Review.class, id);
        if (review == null) {
            throw new ReviewNotFoundException();
        }
        return review;
    }

    @Override
    public void update(Review review) {
        genericDao.update(review);
    }

    @Override
    public void delete(Review review) {
        genericDao.delete(review);
    }
}
