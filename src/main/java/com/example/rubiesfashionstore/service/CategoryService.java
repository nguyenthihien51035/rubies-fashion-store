package com.example.rubiesfashionstore.service;

import com.example.rubiesfashionstore.dto.response.CategoryResponse;
import com.example.rubiesfashionstore.form.product.CategoryForm;
import jakarta.validation.Valid;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(@Valid CategoryForm form);

    CategoryResponse updateCategory(Integer id, @Valid CategoryForm form);

    void deleteCategory(Integer id);

    CategoryResponse getCategoryById(Integer id);

    List<CategoryResponse> getAllCategory();
}
