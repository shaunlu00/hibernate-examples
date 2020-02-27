package com.shaunlu.github.hibernate.entity;

import lombok.Data;
import org.hibernate.annotations.Subselect;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity(name = "RoomView")
@Subselect("select RANDOM_UUID() as id, a.roomName, b.buildingName " +
        "from ROOM a " +
        "left join BUILDING b on b.id = a.buildingId")
public class RoomView {

    @Id
    private String id;

    private String roomName;

    private String buildingName;

}
