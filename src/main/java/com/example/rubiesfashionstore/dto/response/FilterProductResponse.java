package com.example.rubiesfashionstore.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class FilterProductResponse {
    private Integer id;
    private String name;
    private String sku;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private Boolean inStock;
    private String categoryName;
}
