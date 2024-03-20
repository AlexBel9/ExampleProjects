package com.example.test_spring_one.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "crude")
public class Crude {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name_crude", columnDefinition = "VARCHAR(50) NOT NULL")
    private String name;

    @Column(name = "unit_id", columnDefinition = "INTEGER")
    private int unitId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", insertable = false, updatable = false)
    private Unit unit;

    @OneToMany(mappedBy = "crude",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private List<ProductCrude> productCrudes = new ArrayList<>();
}
