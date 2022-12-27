package com.example.test_spring_one.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
@Data
@Embeddable
public class PrimaryKeyPC implements Serializable {


    @Column (name = "product_id")
    private Integer productId;

    @Column(name = "crude_id")
    private Integer crudeId;
}
