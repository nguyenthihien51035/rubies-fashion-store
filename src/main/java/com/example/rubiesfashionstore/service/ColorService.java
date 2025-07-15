package com.example.rubiesfashionstore.service;

import com.example.rubiesfashionstore.dto.response.ColorResponse;
import com.example.rubiesfashionstore.form.product.ColorForm;
import jakarta.validation.Valid;

import java.util.List;

public interface ColorService {
    ColorResponse createColor(@Valid ColorForm form);
    ColorResponse updateColor(Integer id, @Valid ColorForm form);
    void deleteColor(Integer id);
    ColorResponse getColorById(Integer id);
    List<ColorResponse> getAllColor();
}
