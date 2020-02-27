package com.shaunlu.github.hibernate.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(Person.JPAListener.class)
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private LocalDateTime createTime;

    @PreUpdate
    @PrePersist
    public void setCreateTime() {
        createTime = LocalDateTime.of(2020,2,2,10,0,0);
    }

    public static class JPAListener {
        @PostLoad
        public void postLoad(Person p) {
            p.setName("shaun");
        }
    }
}
