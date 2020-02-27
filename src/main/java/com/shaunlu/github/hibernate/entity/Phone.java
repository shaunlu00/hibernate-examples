package com.shaunlu.github.hibernate.entity;

import lombok.Data;
import org.hibernate.Session;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.tuple.ValueGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "PHONE")
public class Phone {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "uuid")
    private String uid;

    @Column(name = "IP")
    private String ip;

    @Column(name = "MAC")
    private String mac;

    @GeneratorType(type = PhoneAddressValueGenerator.class, when = GenerationTime.ALWAYS)
    // Indicates the value is generated on insert and on update.
    @Column(name = "ADDRESS")
    private String address;

    public static class PhoneAddressValueGenerator implements ValueGenerator<String> {
        @Override
        public String generateValue(Session session, Object owner) {
            Phone phone = (Phone) owner;
            return phone.ip + "---" + phone.mac;
        }
    }

    @Column(name = "RATE")
    private Double rate;

    @Column(name = "PRICE")
    private Double price;

    @Formula(value = "RATE * PRICE")
    @Column(name = "BILL")
    private Double bill;

}
