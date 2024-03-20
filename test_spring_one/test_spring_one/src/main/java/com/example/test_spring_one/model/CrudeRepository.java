package com.example.test_spring_one.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CrudeRepository extends JpaRepository<Crude, Integer> {
    Crude findByName(String name);
}
