package com.example.rubiesfashionstore.controller;

import com.example.rubiesfashionstore.dto.ApiResponse;
import com.example.rubiesfashionstore.dto.response.FilterProductResponse;
import com.example.rubiesfashionstore.dto.response.ProductResponse;
import com.example.rubiesfashionstore.form.product.FilterProductForm;
import com.example.rubiesfashionstore.form.product.ProductForm;
import com.example.rubiesfashionstore.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {
    private final ProductService productService;

    //Tạo sản phẩm
    @PostMapping
    public ResponseEntity<ApiResponse> createProduct(@Valid @RequestBody ProductForm form) {
        ProductResponse productRes = productService.createProduct(form);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Tạo sản phẩm thành công", productRes));
    }

    //Lấy sản phẩm theo id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Integer id) {
        ProductResponse product = productService.getProductById(id);
        return ResponseEntity.ok(new ApiResponse("Chi tiết sản phẩm", product));
    }

    //Cập nhật sản phẩm
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Integer id, @Valid @RequestBody ProductForm form) {
        ProductResponse productResponse = productService.updateProduct(id, form);
        return ResponseEntity.ok(new ApiResponse("Cập nhật sản phẩm thành công", productResponse));
    }

    //Xóa sản phẩm
    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(new ApiResponse("Xóa sản phẩm thành công", null));
    }

    //Filter
    @PostMapping("filter")
    public ResponseEntity<ApiResponse> filterProducts(@RequestBody FilterProductForm filter) {
        Page<FilterProductResponse> page = productService.filterProducts(filter);
        return ResponseEntity.ok(new ApiResponse("Lọc sản phẩm thành công", page));
    }
}
