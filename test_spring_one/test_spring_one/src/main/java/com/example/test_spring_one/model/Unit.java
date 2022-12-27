package com.example.test_spring_one.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
@Table(name = "unit")
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name_unit", columnDefinition = "VARCHAR(50) NOT NULL")
    private String name;

    @OneToMany(mappedBy = "unit",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Crude> crudeList;
}
