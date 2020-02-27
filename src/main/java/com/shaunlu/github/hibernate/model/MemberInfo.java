package com.shaunlu.github.hibernate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfo {
    private Boolean vip;
    private Integer loyaltyPoints;
}
