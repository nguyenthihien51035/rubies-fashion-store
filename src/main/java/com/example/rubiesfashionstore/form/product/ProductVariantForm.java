package com.example.rubiesfashionstore.form.product;

import com.example.rubiesfashionstore.model.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductVariantForm {
    private Integer colorId;
    private String colorName;
    private Size size;
    private Integer quantity;
    private String imageUrl;
    private String colorImageUrl;
}
