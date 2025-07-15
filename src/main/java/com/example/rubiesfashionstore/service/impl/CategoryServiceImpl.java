package com.example.rubiesfashionstore.service.impl;

import com.example.rubiesfashionstore.dto.response.CategoryResponse;
import com.example.rubiesfashionstore.exception.BusinessException;
import com.example.rubiesfashionstore.exception.ErrorCodeConstant;
import com.example.rubiesfashionstore.exception.NotFoundException;
import com.example.rubiesfashionstore.form.product.CategoryForm;
import com.example.rubiesfashionstore.model.Category;
import com.example.rubiesfashionstore.modelmapper.CategoryMapper;
import com.example.rubiesfashionstore.repository.CategoryRepository;
import com.example.rubiesfashionstore.service.CategoryService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    public final CategoryRepository categoryRepository;
    public final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    @Transactional
    public CategoryResponse createCategory(CategoryForm form) {
        if (categoryRepository.existsByName(form.getName())) {
            throw new BusinessException("Tên danh mục đã tồn tại", ErrorCodeConstant.CATEGORY_NAME_ALREADY_EXISTS);
        }
        Category category = new Category();
        category.setName(form.getName());
        Category saved = categoryRepository.save(category);
        return categoryMapper.mapToResponse(saved);
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(Integer id, CategoryForm form) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy danh mục trong hệ thống", ErrorCodeConstant.CATEGORY_NOT_FOUND_BY_ID));

        if (categoryRepository.existsByName(form.getName())) {
            throw new BusinessException("Tên danh mục đã tồn tại", ErrorCodeConstant.CATEGORY_NAME_ALREADY_EXISTS);
        }
        category.setName(form.getName());
        categoryRepository.save(category);

        return categoryMapper.mapToResponse(category);
    }

    @Override
    @Transactional
    public void deleteCategory(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy danh mục trong hệ thống", ErrorCodeConstant.CATEGORY_NOT_FOUND_BY_ID));
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryResponse getCategoryById(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Không tìm thấy danh mục trong hệ thống", ErrorCodeConstant.CATEGORY_NOT_FOUND_BY_ID));
        return categoryMapper.mapToResponse(category);
    }

    @Override
    public List<CategoryResponse> getAllCategory() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::mapToResponse)
                .collect(Collectors.toList());
    }
}
