package com.example.rubiesfashionstore.service;

import com.example.rubiesfashionstore.dto.response.FilterProductResponse;
import com.example.rubiesfashionstore.dto.response.ProductResponse;
import com.example.rubiesfashionstore.form.product.FilterProductForm;
import com.example.rubiesfashionstore.form.product.ProductForm;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

public interface ProductService {

    ProductResponse getProductById(Integer id);

    ProductResponse createProduct(@Valid ProductForm createProduct);

    ProductResponse updateProduct(Integer id, @Valid ProductForm updateProduct);

    void deleteProduct(Integer id);

    Page<FilterProductResponse> filterProducts(FilterProductForm filterProduct);
}