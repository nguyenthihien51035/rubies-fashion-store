package com.example.rubiesfashionstore.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
@Getter
@Setter
public class ProductResponse {
    private Integer id;
    private String name;
    private String sku;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private Boolean inStock;
    private String categoryName;
    private List<String> imageUrls;

    private List<ProductVariantResponse> variants;
}
