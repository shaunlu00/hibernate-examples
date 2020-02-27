package com.shaunlu.github.hibernate.test;

import com.shaunlu.github.hibernate.entity.Book;
import com.shaunlu.github.hibernate.entity.Phone;
import com.shaunlu.github.hibernate.entity.Publisher;
import com.shaunlu.github.hibernate.util.HibernateHelper;
import org.junit.Test;

import javax.persistence.Query;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EmbedAndInheritanceTest {

    @Test
    public void testEmbeddedEntity() {
        HibernateHelper.doInTransaction(entityManager -> {
            Book book = new Book();
            book.setPublisher(new Publisher("Wifly", new Publisher.Location("No 1 St")));
            entityManager.persist(book);
        });
    }

    @Test
    public void testInheritance() {
    }}
