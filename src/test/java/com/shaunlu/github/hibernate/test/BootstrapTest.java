package com.shaunlu.github.hibernate.test;

import com.shaunlu.github.hibernate.entity.Employee;
import com.shaunlu.github.hibernate.util.HibernateHelper;
import org.junit.Test;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BootstrapTest {

    @Test
    public void testNativeBootstrap() {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        try {
            entityManager = HibernateHelper.getEntityManagerFactory().createEntityManager();
            // start a transaction and commit
            transaction = entityManager.getTransaction();
            transaction.begin();
            Employee employee = new Employee();
            employee.setCode("12901");
            employee.setName("Mike Lewis");
            entityManager.persist(employee);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
        Query query = entityManager.createQuery("select e from Employee e where e.code = '12901'");
        Employee e = (Employee) query.getSingleResult();
        assertNotNull(e);
        assertEquals("12901", e.getCode());
    }
}
