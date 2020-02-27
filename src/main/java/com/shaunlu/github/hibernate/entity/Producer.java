package com.shaunlu.github.hibernate.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Producer {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "producer")
    private List<Movie> movies = new ArrayList<>();

    public Producer(){}

    public Producer(String name) {
        this.name = name;
    }

}
