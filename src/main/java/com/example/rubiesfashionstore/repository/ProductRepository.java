package com.example.rubiesfashionstore.repository;

import com.example.rubiesfashionstore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    boolean existsBySku(String sku);

    //Tìm kiếm theo danh mục
    List<Product> findByCategoryId(Integer categoryId);

    //Tìm kiếm theo tên
    List<Product> findByNameContainingIgnoreCase(String keyword);

    //Tìm kiếm sản phẩm còn hàng
    List<Product> findByInStockTrue();

    //Tìm kiếm theo sku (mã sản phẩm unique)
    Optional<Product> findBySku(String sku);
}
