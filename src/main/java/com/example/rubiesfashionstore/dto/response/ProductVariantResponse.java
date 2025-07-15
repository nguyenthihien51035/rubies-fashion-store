package com.example.rubiesfashionstore.dto.response;

public class ProductVariantResponse {
    private Integer colorId;
    private String colorName;
    private String colorImageUrl;
    private String size;
    private Integer quantity;

    public ProductVariantResponse(Integer colorId, String colorName, String colorImageUrl, String size, Integer quantity) {
        this.colorId = colorId;
        this.colorName = colorName;
        this.colorImageUrl = colorImageUrl;
        this.size = size;
        this.quantity = quantity;
    }

    public ProductVariantResponse() {

    }

    public Integer getColorId() {
        return colorId;
    }

    public void setColorId(Integer colorId) {
        this.colorId = colorId;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getColorImageUrl() {
        return colorImageUrl;
    }

    public void setColorImageUrl(String colorImageUrl) {
        this.colorImageUrl = colorImageUrl;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
