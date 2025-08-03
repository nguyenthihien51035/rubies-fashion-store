package com.example.rubiesfashionstore.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductVariantResponse {
    private Integer colorId;
    private String colorName;
    private String colorImageUrl;
    private String size;
    private Integer quantity;

    public ProductVariantResponse() {
    }

    public ProductVariantResponse(Integer colorId, String colorName, String colorImageUrl, String size, Integer quantity) {
        this.colorId = colorId;
        this.colorName = colorName;
        this.colorImageUrl = colorImageUrl;
        this.size = size;
        this.quantity = quantity;
    }
}
