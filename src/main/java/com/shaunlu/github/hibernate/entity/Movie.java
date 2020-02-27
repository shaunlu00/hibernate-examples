package com.shaunlu.github.hibernate.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Movie {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public Movie(){}

    public Movie(String name, Producer producer) {
        this.name = name;
        this.producer = producer;
    }

    @ManyToOne
    @JoinColumn(name = "producer_id")
    private Producer producer;

}
