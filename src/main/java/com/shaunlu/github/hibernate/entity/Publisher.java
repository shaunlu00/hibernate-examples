package com.shaunlu.github.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Publisher {
    private String name;
    private Location location;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class Location {
        private String address;
    }
}
