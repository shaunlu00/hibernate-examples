package com.shaunlu.github.hibernate.entity;

import com.shaunlu.github.hibernate.model.CustomerInfo;
import com.shaunlu.github.hibernate.model.MemberInfo;
import com.shaunlu.github.hibernate.type.MemberInfoAttributeConverter;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@Entity
@Table(name = "CUSTOMER")
public class Customer {

    @Id
    @Column(name = "UID")
    private String uid;

    @Column(name = "CUS_INFO")
    @Type(type = "cus_info_type")
    private CustomerInfo customerInfo;

    @Column(name = "MEM_INFO")
    @Convert(converter = MemberInfoAttributeConverter.class)
    private MemberInfo memberInfo;

}
