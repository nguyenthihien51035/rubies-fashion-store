package com.example.rubiesfashionstore.controller;

import com.example.rubiesfashionstore.dto.ApiResponse;
import com.example.rubiesfashionstore.dto.response.ColorResponse;
import com.example.rubiesfashionstore.form.product.CategoryForm;
import com.example.rubiesfashionstore.form.product.ColorForm;
import com.example.rubiesfashionstore.modelmapper.ColorMapper;
import com.example.rubiesfashionstore.repository.ColorRepository;
import com.example.rubiesfashionstore.service.ColorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/colors")
public class ColorController {
    public final ColorService colorService;
    public final ColorMapper colorMapper;

    public ColorController(ColorService colorService, ColorMapper colorMapper) {
        this.colorService = colorService;
        this.colorMapper = colorMapper;
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> createColor(@Valid @RequestBody ColorForm form) {
        ColorResponse created = colorService.createColor(form);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Thành công", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateColor(@PathVariable Integer id, @Valid @RequestBody ColorForm form) {
        ColorResponse updated = colorService.updateColor(id, form);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Cập nhật thành công", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteColor(@PathVariable Integer id) {
        colorService.deleteColor(id);
        return ResponseEntity.ok(new ApiResponse("Thành công", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getColorById(@PathVariable Integer id) {
        ColorResponse response = colorService.getColorById(id);
        return ResponseEntity.ok(new ApiResponse("Truy suất thành công", response));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getAllColor() {
        List<ColorResponse> colors = colorService.getAllColor();
        return ResponseEntity.ok(new ApiResponse("Thành công", colors));
    }
}
