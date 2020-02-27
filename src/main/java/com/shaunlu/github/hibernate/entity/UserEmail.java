package com.shaunlu.github.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
@Audited
public class UserEmail {
    @Id
    @GeneratedValue
    private Long id;

    private String userId;

    private String email;

    public UserEmail(String userId, String email) {
        this.userId = userId;
        this.email = email;
    }
}
