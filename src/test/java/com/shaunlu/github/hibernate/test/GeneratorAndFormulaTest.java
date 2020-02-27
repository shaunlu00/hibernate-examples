package com.shaunlu.github.hibernate.test;

import com.shaunlu.github.hibernate.entity.Phone;
import com.shaunlu.github.hibernate.util.HibernateHelper;
import org.junit.Test;

import javax.persistence.Query;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GeneratorAndFormulaTest {

    @Test
    public void testPropertyGeneration() {
        HibernateHelper.doInTransaction(entityManager -> {
            Phone phone = new Phone();
            phone.setIp("192.168.11.15");
            phone.setMac("xx1xx");
            entityManager.persist(phone);
        });
        HibernateHelper.doQuery(entityManager -> {
            Query query = entityManager.createQuery("select p from Phone p");
            Phone p = (Phone) query.getSingleResult();
            assertNotNull(p);
            assertEquals("192.168.11.15---xx1xx", p.getAddress());
        });
    }

    @Test
    public void testFormula() {
        HibernateHelper.doInTransaction(entityManager -> {
            Phone phone = new Phone();
            phone.setPrice(1000.0);
            phone.setRate(0.1);
            entityManager.persist(phone);
        });
        HibernateHelper.doQuery(entityManager -> {
            Query query = entityManager.createQuery("select p from Phone p");
            Phone p = (Phone) query.getSingleResult();
            assertNotNull(p);
            assertEquals(100, p.getBill().intValue());
        });
    }}
