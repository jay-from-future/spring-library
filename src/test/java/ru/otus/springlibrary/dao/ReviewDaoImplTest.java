package ru.otus.springlibrary.dao;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.springlibrary.domain.Review;
import ru.otus.springlibrary.exception.ReviewNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import({ReviewDaoImpl.class, GenericDaoImpl.class})
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class ReviewDaoImplTest {

    private static final String TEST_DB_REVIEW = "test db review";

    private static final String TEST_DB_TITLE = "test db title";

    @Autowired
    ReviewDao reviewDao;

    @Test
    @Transactional
    void findById() throws ReviewNotFoundException {
        long id = 1L;
        Review review = reviewDao.findById(id);

        assertEquals(id, review.getId());
        assertEquals(TEST_DB_REVIEW, review.getReview());
        assertNotNull(review.getBook());
        assertEquals(TEST_DB_TITLE, review.getBook().getTitle());
    }

}