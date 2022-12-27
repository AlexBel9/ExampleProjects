package com.example.test_spring_one.services;

import com.example.test_spring_one.model.CrudeRepository;
import com.example.test_spring_one.model.PCRepository;
import com.example.test_spring_one.model.ProductRepository;
import org.springframework.stereotype.Repository;

@Repository
public class GeneralRepository {


    public GeneralRepository(CrudeRepository crudeRepository, PCRepository pcRepository, ProductRepository productRepository) {
        this.crudeRepository = crudeRepository;
        this.pcRepository = pcRepository;
        this.productRepository = productRepository;
    }

    CrudeRepository crudeRepository;
    PCRepository pcRepository;
    ProductRepository productRepository;


}
