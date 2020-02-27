package com.shaunlu.github.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "ROOM")
public class Room {

    @Id
    private String id;

    private String roomName;

    private Long buildingId;

}
