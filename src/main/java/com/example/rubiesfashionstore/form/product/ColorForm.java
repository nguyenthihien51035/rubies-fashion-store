package com.example.rubiesfashionstore.form.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ColorForm {
    @NotBlank(message = "Tên màu không được để trống")
    private String name;

    @Pattern(regexp = "^#([A-Fa-f0-9]{6})$", message = "Mã màu không hợp lệ")
    @NotBlank(message = "Mã hex không được để trống")
    private String hexCode;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHexCode() {
        return hexCode;
    }

    public void setHexCode(String hexCode) {
        this.hexCode = hexCode;
    }
}
