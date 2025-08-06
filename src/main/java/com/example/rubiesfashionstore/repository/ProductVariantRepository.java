package com.example.rubiesfashionstore.repository;

import com.example.rubiesfashionstore.model.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Integer> {
    List<ProductVariant> findByProductId(Integer productId);

    void deleteByProductId(Integer productId);
}
