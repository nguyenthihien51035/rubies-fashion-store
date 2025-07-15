package com.example.rubiesfashionstore.controller;

import com.example.rubiesfashionstore.dto.ApiResponse;
import com.example.rubiesfashionstore.dto.response.FilterProductResponse;
import com.example.rubiesfashionstore.dto.response.ProductResponse;
import com.example.rubiesfashionstore.form.product.CreateAndUpdateProductForm;
import com.example.rubiesfashionstore.form.product.FilterProduct;
import com.example.rubiesfashionstore.model.Product;
import com.example.rubiesfashionstore.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequiredArgsConstructor
@RequestMapping("api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //Tạo sản phẩm
    @PostMapping
    public ResponseEntity<ApiResponse> createProduct(@Valid @RequestBody CreateAndUpdateProductForm form) {
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
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Integer id, @Valid @RequestBody CreateAndUpdateProductForm form) {
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
    public ResponseEntity<ApiResponse> filterProducts(@RequestBody FilterProduct filter) {
        Page<FilterProductResponse> page = productService.filterProducts(filter);
        return ResponseEntity.ok(new ApiResponse("Lọc sản phẩm thành công", page));
    }
}
