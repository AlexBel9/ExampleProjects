package com.example.test_spring_one.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PCRepository extends JpaRepository<ProductCrude, PrimaryKeyPC> {

}
