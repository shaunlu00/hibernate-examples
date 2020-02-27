package com.shaunlu.github.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(
                name = "get_employee_by_name",
                query = "select e from Employee e where e.name = :name"
        )
})

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "EMPLOYEE")
public class Employee {

    @Id
    @Column(name = "CODE")
    private String code;

    @Column(name = "NAME")
    private String name;

    // Default BasicType
    @Column(name = "DEFAULT_BOOL")
    private Boolean defaultBoolean; // true or false

    // Specify BasicType
    @org.hibernate.annotations.Type(type = "numeric_boolean") // 0 is false, 1 is true
    @Column(name = "NUMERIC_BOOL")
    private Boolean numericBoolean;

    // Define SQLType
    @Column(name = "CHAR_BOOL", columnDefinition = "CHAR(10)") // 'True' or 'False'
    private Boolean charBoolean;
}
