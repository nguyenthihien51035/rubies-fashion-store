package com.example.rubiesfashionstore.service;

import com.example.rubiesfashionstore.dto.response.FilterProductResponse;
import com.example.rubiesfashionstore.dto.response.ProductResponse;
import com.example.rubiesfashionstore.form.product.CreateAndUpdateProductForm;
import com.example.rubiesfashionstore.form.product.FilterProduct;
import com.example.rubiesfashionstore.model.Product;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ProductService {

    ProductResponse getProductById(Integer id);

    ProductResponse createProduct(@Valid CreateAndUpdateProductForm createProduct);

    ProductResponse updateProduct(Integer id, @Valid CreateAndUpdateProductForm updateProduct);

    void deleteProduct(Integer id);

    Page<FilterProductResponse> filterProducts(FilterProduct filterProduct);

}
