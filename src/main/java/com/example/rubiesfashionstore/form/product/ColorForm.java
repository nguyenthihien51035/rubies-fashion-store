package com.example.rubiesfashionstore.form.product;

import jakarta.validation.constraints.NotBlank;

public class ColorForm {
    @NotBlank(message = "Tên màu không được để trống")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
