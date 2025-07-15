package com.example.rubiesfashionstore.modelmapper;

import com.example.rubiesfashionstore.dto.response.CategoryResponse;
import com.example.rubiesfashionstore.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryResponse mapToResponse(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        return response;
    }
}

