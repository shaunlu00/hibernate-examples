package com.shaunlu.github.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OptimisticLock;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "ROBOT")
public class Robot {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OptimisticLock(excluded = true)
    private String type;

    @Version
    private Long version;
}
