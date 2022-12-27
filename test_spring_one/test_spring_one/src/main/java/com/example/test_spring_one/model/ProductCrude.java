package com.example.test_spring_one.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ProductCrude {

    @EmbeddedId
    @Column(name = "product_crude")
    private PrimaryKeyPC productCrude;


    @ManyToOne
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "product_id", insertable=false, updatable=false)
    private Product product;

    @ManyToOne
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "crude_id", insertable=false, updatable=false)
    private Crude crude;

    private int count;
    
}
