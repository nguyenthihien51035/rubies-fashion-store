package com.example.rubiesfashionstore.form.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryForm {
    @NotBlank(message = "Tên danh mục không được để trống")
    private String name;
    @NotBlank(message = "Ảnh danh mục không được để trống")
    private String image;
}
