package com.shaunlu.github.hibernate.test;

import com.shaunlu.github.hibernate.entity.Building;
import com.shaunlu.github.hibernate.entity.Room;
import com.shaunlu.github.hibernate.entity.RoomView;
import com.shaunlu.github.hibernate.util.HibernateHelper;
import org.junit.Test;

import javax.persistence.Query;
import java.util.List;

public class EntityTest {

    @Test
    public void testSubSelect() {
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
            Query query = entityManager.createQuery("select e from RoomView e");
            List<RoomView> roomViews = query.getResultList();
            roomViews.stream().forEach(roomView -> {
                System.out.println(roomView.getRoomName() + "-" + roomView.getBuildingName() + "\n");
            });
        });
    }
}
