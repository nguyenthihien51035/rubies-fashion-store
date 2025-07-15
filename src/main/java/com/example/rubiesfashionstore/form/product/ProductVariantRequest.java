package com.example.rubiesfashionstore.form.product;

import com.example.rubiesfashionstore.model.Size;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
public class ProductVariantRequest {
    private Integer colorId;

    private String colorName;
    private Size size;
    private Integer quantity;
    private String imageUrl;

    private String colorImageUrl;

    public String getColorImageUrl() {
        return colorImageUrl;
    }

    public void setColorImageUrl(String colorImageUrl) {
        this.colorImageUrl = colorImageUrl;
    }

    public Integer getColorId() {
        return colorId;
    }

    public void setColorId(Integer colorId) {
        this.colorId = colorId;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }
}
