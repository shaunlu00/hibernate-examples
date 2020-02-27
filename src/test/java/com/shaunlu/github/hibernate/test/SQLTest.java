package com.shaunlu.github.hibernate.test;

import com.shaunlu.github.hibernate.entity.Building;
import com.shaunlu.github.hibernate.entity.Employee;
import com.shaunlu.github.hibernate.entity.Room;
import com.shaunlu.github.hibernate.util.HibernateHelper;
import org.junit.Test;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;


public class SQLTest {



    private void prepareTestData(){
        HibernateHelper.doInTransaction(entityManager -> {
            Employee employee1 = Employee.builder().code("A0001").name("Jack").build();
            Employee employee2 = Employee.builder().code("A0002").name("Peter").build();
            entityManager.persist(employee1);
            entityManager.persist(employee2);
        });
    }

    @Test
    public void testSelectUpdateDelete(){
        prepareTestData();
        // select
        HibernateHelper.doQuery(entityManager -> {
            List<Employee> employees = entityManager.createQuery("select e from Employee e", Employee.class).getResultList();
            assertEquals(2, employees.size());
            // named query
            Employee e = entityManager.createNamedQuery("get_employee_by_name", Employee.class)
                    .setParameter("name", "Jack").getSingleResult();
            assertNotNull(e);
            // dynamic instantiation
            // 1 constructor expression
            List<Employee> employees1 = entityManager.createQuery(
                    "select new com.shaunlu.github.hibernate.entity.Employee(" +
                            "e.code, e.name, e.defaultBoolean, e.numericBoolean, e.charBoolean) " +
                            "from Employee e", Employee.class).getResultList();
            assertEquals(2, employees1.size());
            // 2 list
            List<List> employees2 = entityManager.createQuery(
                    "select new list(" +
                            "e.code, e.name, e.defaultBoolean, e.numericBoolean, e.charBoolean) " +
                            "from Employee e", List.class).getResultList();
            assertEquals(2, employees2.size());
            // 3 map
            List<Map> employees3 = entityManager.createQuery(
                    "select new map(" +
                            "e.code, e.name, e.defaultBoolean, e.numericBoolean, e.charBoolean) " +
                            "from Employee e", Map.class).getResultList();
            assertEquals(2, employees2.size());
        });

        // update
        HibernateHelper.doInTransaction(entityManager -> {
            int updatedEntities = entityManager.createQuery(
                    "update Employee e " +
                            "set e.name = :newName " +
                            "where e.name = :oldName" )
                    .setParameter( "oldName", "Jack" )
                    .setParameter( "newName", "Steve" )
                    .executeUpdate();
            assertEquals(1, updatedEntities);
        });

        //delete
        HibernateHelper.doInTransaction(entityManager -> {
            int updatedEntities = entityManager.createQuery(
                    "delete Employee e " +
                            "where e.name = :param_name" )
                    .setParameter( "param_name", "Steve" )
                    .executeUpdate();
            assertEquals(1, updatedEntities);
        });
    }

    @Test
    public void testJoin(){
        HibernateHelper.doInTransaction(entityManager -> {
            Building building = new Building();
            building.setBuildingName("Building X");
            entityManager.persist(building);
            Room room1 = new Room("R1", "Room1", building.getId());
            Room room2 = new Room("R2", "Room2", building.getId());
            entityManager.persist(room1);
            entityManager.persist(room2);
        });
        HibernateHelper.doQuery(entityManager -> {
           List<Object[]>  result = entityManager.createQuery(
                    "select a, b " +
                    "from Room a " +
                    "left join Building b on b.id = a.buildingId").getResultList();
           assertEquals(2, result.size());
           Object[] firstResult = result.get(0);
           assertEquals(2, firstResult.length);
           assertEquals("Building X", ((Building) firstResult[1]).getBuildingName());
        });
    }

}
