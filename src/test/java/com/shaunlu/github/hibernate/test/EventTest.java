package com.shaunlu.github.hibernate.test;

import com.shaunlu.github.hibernate.entity.Person;
import com.shaunlu.github.hibernate.entity.Robot;
import com.shaunlu.github.hibernate.util.HibernateHelper;
import com.shaunlu.github.hibernate.util.LoggingInterceptor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class EventTest {
    @Test
    public void testInterceptor() {
        EntityManagerFactory emf = HibernateHelper.getEMF();
        SessionFactory sessionFactory = emf.unwrap(SessionFactory.class);
        Session session = sessionFactory.withOptions().interceptor(new LoggingInterceptor()).openSession();
        session.getTransaction().begin();
        Robot robot = new Robot();
        robot.setName("robot");
        session.save(robot);
        session.getTransaction().commit();
    }
    @Test
    public void testJPACallbacks() {
        HibernateHelper.doInTransaction(entityManager -> {
            Person person = new Person();
            entityManager.persist(person);
        });
        HibernateHelper.doQuery(entityManager -> {
            Person person = entityManager.createQuery("select p from Person p", Person.class).getSingleResult();
            assertEquals("shaun", person.getName());
            assertTrue(LocalDateTime.of(2020,2,2,10,0,0).isEqual(person.getCreateTime()));
        });
    }
}
