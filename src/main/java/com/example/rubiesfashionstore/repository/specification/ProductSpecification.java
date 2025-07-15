package com.example.rubiesfashionstore.repository.specification;

import com.example.rubiesfashionstore.model.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecification {

    public static Specification<Product> alwaysTrue() {
        return (root, query, cb) -> cb.conjunction();
    }

    public static Specification<Product> hasName(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if(keyword == null || keyword.isBlank())
                return null;
            return  criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + keyword.toLowerCase() + "%");
        };
    }

    public static Specification<Product> hasCategory(Integer categoryId) {
        return (root, query, criteriaBuilder) -> {
            if (categoryId == null)
                return null;
            return criteriaBuilder.equal(root.get("category").get("id"), categoryId);
        };
    }

    public static Specification<Product> hasSku(String sku) {
        return (root, query, criteriaBuilder) -> {
            if (sku == null || sku.isBlank())
                return null;
            return criteriaBuilder.equal(root.get("sku"), sku);
        };
    }

    public static Specification<Product> priceBetween(BigDecimal min, BigDecimal max) {
        return (root, query, criteriaBuilder) -> {
            if(min == null || max == null)
                return null;
            return criteriaBuilder.between(root.get("price"), min, max);
        };
    }

    //Lọc sản phẩm có giảm giá
    public static Specification<Product> hasDiscount(Boolean hasDiscount) {
        return (root, query, criteriaBuilder) -> {
            if (Boolean.FALSE.equals(hasDiscount)) return null;
            return criteriaBuilder.lessThan(root.get("discountPrice"), root.get("price"));
        };
    }

    public static Specification<Product> isInStock(Boolean inStock) {
        return (root, query, criteriaBuilder) -> {
            if(inStock == null || !inStock)
                return null;
            return criteriaBuilder.isTrue((root.get("inStock")));
        };
    }
}
