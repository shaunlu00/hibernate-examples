package com.shaunlu.github.hibernate.test;

import com.shaunlu.github.hibernate.entity.Robot;
import com.shaunlu.github.hibernate.util.HibernateHelper;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class LockingTest {

    @Test
    public void testLocking() throws InterruptedException {
        HibernateHelper.doInTransaction(entityManager -> {
            Robot robot = new Robot();
            robot.setName("robot1");
            entityManager.persist(robot);
        });
        // One of the thread will fail to update the record because a OptimisticLockException is thrown and caught
        Thread t1 = new Thread(new LockingTestThread1());
        Thread t2 = new Thread(new LockingTestThread1());
        t1.start();t2.start();
        t1.join();t2.join();
        Thread.sleep(2000);
        HibernateHelper.doQuery(entityManager -> {
            Robot robot = entityManager.createQuery("select r from Robot r " +
                    "where r.name='robot2' and r.version=1 ", Robot.class).getSingleResult();
            assertNotNull(robot);
        });
    }

    @Test
    public void testLockingWithExcludedAttribute() throws InterruptedException {
        HibernateHelper.doInTransaction(entityManager -> {
            Robot robot = new Robot();
            robot.setName("robot1");
            robot.setType("type1");
            entityManager.persist(robot);
        });
        // The two threads will update the record successfully without exception thrown, but one will override change to another
        Thread t1 = new Thread(new LockingTestThread1());
        Thread t2 = new Thread(new LockingTestThread2());
        t1.start();t2.start();
        t1.join();t2.join();
        Thread.sleep(2000);
        HibernateHelper.doQuery(entityManager -> {
            Robot robot = entityManager.createQuery("select r from Robot r " +
                    "where r.name='robot2' and r.type='typ1' and r.version=1", Robot.class).getSingleResult();
            assertNotNull(robot);
        });
    }

    public class LockingTestThread1 implements Runnable {
        public void run() {
            HibernateHelper.doInTransaction(entityManager -> {
                Robot robot = entityManager.createQuery("select r from Robot r where r.name='robot1'", Robot.class).getSingleResult();
                // sleep 2 seconds
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                robot.setName("robot2");
                entityManager.persist(robot);
            });
        }
    }

    public class LockingTestThread2 implements Runnable {
        public void run() {
            HibernateHelper.doInTransaction(entityManager -> {
                Robot robot = entityManager.createQuery("select r from Robot r where r.name='robot1'", Robot.class).getSingleResult();
                // sleep 2 seconds
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                robot.setType("type2");
                entityManager.persist(robot);
            });
        }
    }

}
