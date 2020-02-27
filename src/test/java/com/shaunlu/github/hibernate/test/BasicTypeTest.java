package com.shaunlu.github.hibernate.test;

import com.shaunlu.github.hibernate.entity.Customer;
import com.shaunlu.github.hibernate.entity.Employee;
import com.shaunlu.github.hibernate.entity.Product;
import com.shaunlu.github.hibernate.model.CustomerInfo;
import com.shaunlu.github.hibernate.model.MemberInfo;
import com.shaunlu.github.hibernate.util.HibernateHelper;
import org.junit.Test;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.Date;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BasicTypeTest {

    @Test
    public void testExplicitlyMapping() {
        HibernateHelper.doInTransaction(entityManager -> {
            Employee employee = new Employee();
            employee.setCode("12901");
            employee.setDefaultBoolean(true);
            employee.setNumericBoolean(true);
            employee.setCharBoolean(true);
            entityManager.persist(employee);
        });
        HibernateHelper.doQuery(entityManager -> {
            Query query = entityManager.createQuery("select e from Employee e where e.code = '12901'");
            Employee e = (Employee) query.getSingleResult();
            assertTrue(e.getDefaultBoolean());
            assertTrue(e.getNumericBoolean());
            assertTrue(e.getCharBoolean());
        });
    }

    @Test
    public void testCustomMapping() {
        HibernateHelper.doInTransaction(entityManager -> {
            Customer customer = new Customer();
            customer.setUid("111");
            customer.setCustomerInfo(new CustomerInfo("peter", "CHN"));
            customer.setMemberInfo(new MemberInfo(true, 50));
            entityManager.persist(customer);
        });
        HibernateHelper.doQuery(entityManager -> {
            Query query1 = entityManager.createQuery("select c from Customer c where c.customerInfo = ?1");
            query1.setParameter(1, new CustomerInfo("peter", "CHN"));
            Customer e1 = (Customer) query1.getSingleResult();
            assertEquals("111", e1.getUid());

            Query query2 = entityManager.createQuery("select c from Customer c where c.memberInfo = ?1");
            query2.setParameter(1, new MemberInfo(true, 50));
            Customer e2 = (Customer) query2.getSingleResult();
            assertEquals("peter", e2.getCustomerInfo().getName());
            assertEquals("CHN", e2.getCustomerInfo().getAddress());
        });
    }

    @Test
    public void testEnumAndLobAndDatetime() {
        HibernateHelper.doInTransaction(entityManager -> {
            Product product = new Product("123", Product.ProductType.VIP, Product.ProductType.VIP2, new Date(), new Date(), new Date(), LocalDate.now(), "test");
            entityManager.persist(product);
        });
    }

}
