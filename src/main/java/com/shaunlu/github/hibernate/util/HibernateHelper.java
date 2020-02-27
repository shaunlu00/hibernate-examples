package com.shaunlu.github.hibernate.util;

import com.shaunlu.github.hibernate.entity.*;
import com.shaunlu.github.hibernate.type.CustomerInfoType;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.sql.Connection;
import java.util.Properties;

@Slf4j
public class HibernateHelper {

    public static EntityManagerFactory entityManagerFactory;

    static {
        Properties props = new Properties();
        props.put("hibernate.hbm2ddl.auto", "update");
        // Use HikariCP as an connection provider
        props.put("hibernate.connection.provider_class", "org.hibernate.hikaricp.internal.HikariCPConnectionProvider");
        // Connection properties
        props.put("hibernate.connection.driver_class", "org.h2.Driver");
        props.put("hibernate.connection.url", "jdbc:h2:mem:db1;DB_CLOSE_DELAY=-1;MVCC=TRUE");
        props.put("hibernate.connection.username", "sa");
        props.put("hibernate.connection.password", "");
        props.put("hibernate.connection.isolation", Connection.TRANSACTION_REPEATABLE_READ);
        // Hikari settings
        props.put("hibernate.hikari.minimumIdle", "5");
        props.put("hibernate.hikari.maximumPoolSize", "100");
        props.put("hibernate.hikari.idleTimeout", "3000");
        // Show SQL
        props.put("show_sql", true);
        props.put("format_sql", true);
        props.put("use_sql_comments", true);
        // Audit Strategy
        props.put("org.hibernate.envers.audit_strategy", "org.hibernate.envers.strategy.ValidityAuditStrategy");

        // Build service registry
        ServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().applySettings(props).build();
        // Build metadata
        MetadataSources sources = new MetadataSources(standardRegistry);
        sources.addAnnotatedClass(Employee.class);
        sources.addAnnotatedClass(Customer.class);
        sources.addAnnotatedClass(Product.class);
        sources.addAnnotatedClass(Phone.class);
        sources.addAnnotatedClass(Book.class);
        sources.addAnnotatedClass(Room.class);
        sources.addAnnotatedClass(Building.class);
        sources.addAnnotatedClass(RoomView.class);
        sources.addAnnotatedClass(Producer.class);
        sources.addAnnotatedClass(Movie.class);
        sources.addAnnotatedClass(Robot.class);
        sources.addAnnotatedClass(Person.class);
        sources.addAnnotatedClass(UserEmail.class);
        MetadataBuilder metadataBuilder = sources.getMetadataBuilder();
        metadataBuilder.applyBasicType(CustomerInfoType.INSTANCE);
        Metadata metadata = metadataBuilder.build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        entityManagerFactory = sessionFactory;
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public static void doHQL(HibernateNativeExecutor executor) {
        SessionFactory sessionFactory = (SessionFactory) entityManagerFactory;
        Session session = sessionFactory.getCurrentSession();
        EntityTransaction transaction = null;
        try {
            // start a transaction and commit
            transaction = session.getTransaction();
            transaction.begin();
            executor.execute(session);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                log.error("SQL Execution Error", e);
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public static void doInTransaction(HibernateExecutor executor) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;
        try {
            // start a transaction and commit
            transaction = entityManager.getTransaction();
            transaction.begin();
            executor.execute(entityManager);
            transaction.commit();
        } catch (Exception e) {
            log.error("SQL Execution Error", e);
            if (transaction != null && (transaction.isActive() || transaction.getRollbackOnly())) {
                transaction.rollback();
            }
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public static void doQuery(HibernateExecutor executor) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        executor.execute(entityManager);
    }

    public static EntityManagerFactory getEMF() {
        return entityManagerFactory;
    }
}
