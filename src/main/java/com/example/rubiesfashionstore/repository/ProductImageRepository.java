package com.example.rubiesfashionstore.repository;

import com.example.rubiesfashionstore.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
}
