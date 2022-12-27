package com.example.test_spring_one.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnitRepository extends JpaRepository<Unit, Integer> {
     List<Unit> findByName (String name);
}
