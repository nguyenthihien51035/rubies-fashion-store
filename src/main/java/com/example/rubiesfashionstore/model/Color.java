package com.example.rubiesfashionstore.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "color")
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String name;

    // 1 màu có thể được dùng trong nhiều biến thể sản phẩm
    @OneToMany(mappedBy = "color", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductVariant> productVariants = new ArrayList<>();

    // Getter - Setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductVariant> getProductVariants() {
        return productVariants;
    }

    public void setProductVariants(List<ProductVariant> productVariants) {
        this.productVariants = productVariants;
    }
}