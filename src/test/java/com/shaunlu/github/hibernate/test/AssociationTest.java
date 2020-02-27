package com.shaunlu.github.hibernate.test;

import com.shaunlu.github.hibernate.entity.Movie;
import com.shaunlu.github.hibernate.entity.Phone;
import com.shaunlu.github.hibernate.entity.Producer;
import com.shaunlu.github.hibernate.util.HibernateHelper;
import org.junit.Test;

import javax.persistence.Query;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AssociationTest {

    @Test
    public void testManyToOne() {
        HibernateHelper.doInTransaction(entityManager -> {
            Producer producer = new Producer("Disney");
            entityManager.persist(producer);
            Movie movie1 = new Movie("Lion King", producer);
            Movie movie2 = new Movie("Balabala", producer);
            entityManager.persist(movie1);
            entityManager.persist(movie2);
        });
        HibernateHelper.doQuery(entityManager -> {
            Query query = entityManager.createQuery("select m from Movie m");
            List<Movie> movies = query.getResultList();
            assertNotNull(movies);
            assertEquals("Disney", movies.get(1).getProducer().getName());
        });
    }

    @Test
    public void testBidirectionalOneToMany() {
        HibernateHelper.doInTransaction(entityManager -> {
            Producer producer = new Producer("Disney");
            entityManager.persist(producer);
            Movie movie1 = new Movie("Lion King", producer);
            Movie movie2 = new Movie("Balabala", producer);
            entityManager.persist(movie1);
            entityManager.persist(movie2);
        });
        HibernateHelper.doQuery(entityManager -> {
            Query query = entityManager.createQuery("select p from Producer p");
            Producer producer = (Producer) query.getSingleResult();
            assertEquals(2, producer.getMovies().size());
        });
    }
}
