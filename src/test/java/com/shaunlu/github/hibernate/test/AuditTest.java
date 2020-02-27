package com.shaunlu.github.hibernate.test;

import com.shaunlu.github.hibernate.entity.UserEmail;
import com.shaunlu.github.hibernate.util.HibernateHelper;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AuditTest {

    @Test
    public void test() {
        HibernateHelper.doInTransaction(entityManager -> {
            UserEmail userEmail = new UserEmail("John", "john@gmail.com");
            entityManager.persist(userEmail);
        });
        HibernateHelper.doInTransaction(entityManager -> {
            UserEmail userEmail = entityManager.createQuery("select t from UserEmail t where t.userId = 'John'", UserEmail.class).getSingleResult();
            userEmail.setEmail("john@outlook.com");
            entityManager.persist(userEmail);
        });
        HibernateHelper.doInTransaction(entityManager -> {
            UserEmail userEmail = entityManager.createQuery("select t from UserEmail t where t.userId = 'John'", UserEmail.class).getSingleResult();
            userEmail.setUserId("peter");
            entityManager.persist(userEmail);
        });
        HibernateHelper.doQuery(entityManager -> {
            AuditReader auditReader =AuditReaderFactory.get(entityManager);
            List<Number> revisions = auditReader.getRevisions(UserEmail.class, 1L);
            UserEmail userEmail = (UserEmail) auditReader.createQuery().forEntitiesAtRevision(UserEmail.class, revisions.get(0)).getSingleResult();
            assertEquals("John", userEmail.getUserId());
        });
    }
}
