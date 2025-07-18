package com.example.rubiesfashionstore.repository;

import com.example.rubiesfashionstore.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<Color, Integer> {
    boolean existsByName(String name);
    boolean existsByHexCode(String hexCode);
}
