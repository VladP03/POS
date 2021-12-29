package com.pos.stateless.repostitory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "Data")
@Table(name = "data")
public class Data {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "integer_value")
    private Integer integerValue;

    @Column(name = "double_value")
    private Double doubleValue;
}
