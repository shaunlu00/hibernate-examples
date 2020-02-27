package com.shaunlu.github.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PRODUCT")
public class Product {

    @Id
    @Column(name = "UID")
    private String uid;

    // this column is defined as Integer with value 0, 1
    @Column(name = "PRODUCT_TYPE1")
    private ProductType productType1;

    // this column is defined as String with value 'VIP', 'VIP2'
    @Column(name = "PRODUCT_TYPE2")
    @Enumerated(EnumType.STRING)
    private ProductType productType2;

    public enum ProductType {
        VIP,
        VIP2
    }

    // The JDBC equivalent is java.sql.Date
    @Column(name = "P_DATE")
    @Temporal(TemporalType.DATE)
    private Date date;

    // The JDBC equivalent is java.sql.Time
    @Column(name = "P_TIME")
    @Temporal(TemporalType.TIME)
    private Date time;

    // The JDBC equivalent is java.sql.Timestamp
    @Column(name = "P_DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;

    @Column(name = "JAVA8_TIME")
    private LocalDate localDate;

    @Column(name = "BIGSTR")
    @Lob
    private String bigStr;

}
