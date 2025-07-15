package com.example.rubiesfashionstore.service.impl;

import com.example.rubiesfashionstore.dto.response.FilterProductResponse;
import com.example.rubiesfashionstore.dto.response.ProductResponse;
import com.example.rubiesfashionstore.dto.response.ProductVariantResponse;
import com.example.rubiesfashionstore.exception.BusinessException;
import com.example.rubiesfashionstore.exception.ConflictException;
import com.example.rubiesfashionstore.exception.ErrorCodeConstant;
import com.example.rubiesfashionstore.exception.NotFoundException;
import com.example.rubiesfashionstore.form.product.CreateAndUpdateProductForm;
import com.example.rubiesfashionstore.form.product.FilterProduct;
import com.example.rubiesfashionstore.form.product.ProductVariantRequest;
import com.example.rubiesfashionstore.model.*;
import com.example.rubiesfashionstore.model.Color;
import com.example.rubiesfashionstore.repository.CategoryRepository;
import com.example.rubiesfashionstore.repository.ColorRepository;
import com.example.rubiesfashionstore.repository.ProductRepository;
import com.example.rubiesfashionstore.repository.specification.ProductSpecification;
import com.example.rubiesfashionstore.service.ProductService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ColorRepository colorRepository;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, ColorRepository colorRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.colorRepository = colorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductResponse getProductById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCodeConstant.PRODUCT_NOT_FOUND_BY_ID, "Không tìm thấy sản phẩm với ID: " + id));
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setSku(product.getSku());
        response.setPrice(product.getPrice());
        response.setDiscountPrice(product.getDiscountPrice());
        response.setInStock(product.getInStock());
        response.setCategoryName(product.getCategory().getName());

// Tự map List<String> imageUrls
        List<String> imageUrls = product.getImages().stream()
                .map(ProductImage::getImageUrl)
                .collect(Collectors.toList());


// Tự map List<VariantResponse>
        List<ProductVariantResponse> variants = product.getVariants().stream()
                .map(variant -> new ProductVariantResponse(
                        variant.getColor().getId(),
                        variant.getColor().getName(),
                        variant.getVariantImageUrl(),
                        variant.getSize().name(),
                        variant.getQuantity()
                ))
                .collect(Collectors.toList());

        response.setVariants(variants);
        return response;
    }

    @Override
    @Transactional
    public ProductResponse createProduct(CreateAndUpdateProductForm form) {
        if (productRepository.existsBySku(form.getSku())) {
            throw new ConflictException(ErrorCodeConstant.DUPLICATE_SKU, "SKU đã tồn tại: " + form.getSku());
        }

        Product product = buildProductFromForm(new Product(), form);
        productRepository.save(product);

        // Ánh xạ sang DTO
        ProductResponse dto = modelMapper.map(product, ProductResponse.class);
        dto.setCategoryName(product.getCategory().getName());

        // Lấy danh sách ảnh
        dto.setImageUrls(product.getImages()
                .stream()
                .map(ProductImage::getImageUrl)
                .toList()
        );

        // Lấy danh sách variant
        List<ProductVariantResponse> variantList = product.getVariants()
                .stream()
                .map(variant -> {
                    ProductVariantResponse v = new ProductVariantResponse();
                    v.setColorId(variant.getColor().getId());
                    v.setColorName(variant.getColor().getName());
                    v.setColorImageUrl(variant.getVariantImageUrl());
                    v.setSize(variant.getSize().name());
                    v.setQuantity(variant.getQuantity());
                    return v;
                })
                .toList();

        dto.setVariants(variantList);
        return dto;
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Integer id, @Valid CreateAndUpdateProductForm form) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCodeConstant.PRODUCT_NOT_FOUND_BY_ID,
                        "Không tìm thấy sản phẩm có ID: " + id));

        // SKU: nếu khác & không null thì update & kiểm tra trùng
        if (form.getSku() != null && !form.getSku().equals(existingProduct.getSku())) {
            Optional<Product> productWithSameSku = productRepository.findBySku(form.getSku());
            if (productWithSameSku.isPresent() && !productWithSameSku.get().getId().equals(id)) {
                throw new ConflictException(ErrorCodeConstant.DUPLICATE_SKU, "SKU đã tồn tại: " + form.getSku());
            }
            existingProduct.setSku(form.getSku());
        }

        // Các field đơn giản
        if (form.getName() != null) {
            existingProduct.setName(form.getName());
        }
        if (form.getDescription() != null) {
            existingProduct.setDescription(form.getDescription());
        }
        if (form.getPrice() != null) {
            existingProduct.setPrice(form.getPrice());
        }
        if (form.getDiscountPrice() != null) {
            existingProduct.setDiscountPrice(form.getDiscountPrice());
        }

        // Update category nếu gửi categoryId
        if (form.getCategoryId() != null) {
            Category category = categoryRepository.findById(form.getCategoryId())
                    .orElseThrow(() -> new NotFoundException(ErrorCodeConstant.CATEGORY_NOT_FOUND_BY_ID,
                            "Không tìm thấy danh mục với ID: " + form.getCategoryId()));
            existingProduct.setCategory(category);
        }

        // Ảnh: chỉ update nếu gửi imageUrls
        if (form.getImageUrls() != null) {
            existingProduct.getImages().clear();
            for (String imageUrl : form.getImageUrls()) {
                ProductImage image = new ProductImage();
                image.setImageUrl(imageUrl);
                image.setProduct(existingProduct);
                existingProduct.getImages().add(image);
            }
        }

        // Variants: merge nếu có
        if (form.getVariant() != null) {
            Map<String, ProductVariant> existingMap = existingProduct.getVariants().stream()
                    .collect(Collectors.toMap(
                            v -> v.getColor().getId() + "-" + v.getSize().name(),
                            Function.identity()
                    ));


            for (ProductVariantRequest variantReq : form.getVariant()) {
                String key = variantReq.getColorId() + "-" + variantReq.getSize().name();

                Color color = colorRepository.findById(variantReq.getColorId())
                        .orElseThrow(() -> new NotFoundException(ErrorCodeConstant.COLOR_NOT_FOUND_BY_ID,
                                "Không tìm thấy màu với ID: " + variantReq.getColorId()));

                if (existingMap.containsKey(key)) {
                    ProductVariant variant = existingMap.get(key);
                    variant.setQuantity(variantReq.getQuantity());
                    variant.setVariantImageUrl(variantReq.getColorImageUrl());
                } else {
                    ProductVariant newVariant = new ProductVariant();
                    newVariant.setProduct(existingProduct);
                    newVariant.setColor(color);
                    newVariant.setSize(variantReq.getSize());
                    newVariant.setQuantity(variantReq.getQuantity());
                    newVariant.setVariantImageUrl(variantReq.getColorImageUrl());
                    existingProduct.getVariants().add(newVariant);
                }
            }
        }

        // Lưu lại product
        productRepository.save(existingProduct);

        // Mapping ra DTO
        ProductResponse dto = modelMapper.map(existingProduct, ProductResponse.class);
        dto.setCategoryName(existingProduct.getCategory().getName());

        dto.setImageUrls(existingProduct.getImages()
                .stream()
                .map(ProductImage::getImageUrl)
                .toList());

        List<ProductVariantResponse> variantList = existingProduct.getVariants()
                .stream()
                .map(variant -> {
                    ProductVariantResponse v = new ProductVariantResponse();
                    v.setColorId(variant.getColor().getId());
                    v.setColorName(variant.getColor().getName());
                    v.setColorImageUrl(variant.getVariantImageUrl());
                    v.setSize(variant.getSize().name());
                    v.setQuantity(variant.getQuantity());
                    return v;
                })
                .toList();

        dto.setVariants(variantList);
        return dto;
    }


    @Override
    @Transactional
    public void deleteProduct(Integer id) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundException(ErrorCodeConstant.PRODUCT_NOT_FOUND_BY_ID, "Không tìm thấy sản phẩm với ID: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public Page<FilterProductResponse> filterProducts(FilterProduct filterProduct) {
        Specification<Product> specification = ProductSpecification.alwaysTrue()
                .and(ProductSpecification.hasName(filterProduct.getName()))
                .and(ProductSpecification.hasCategory(filterProduct.getCategoryId()))
                .and(ProductSpecification.hasDiscount(filterProduct.getHasDiscount()))
                .and(ProductSpecification.hasSku(filterProduct.getSku()))
                .and(ProductSpecification.priceBetween(filterProduct.getMinPrice(), filterProduct.getMaxPrice()))
                .and(ProductSpecification.isInStock(filterProduct.getInStock()));
        Pageable pageable = PageRequest.of(filterProduct.getPage(), filterProduct.getSize());
        Page<Product> page = productRepository.findAll(specification, pageable);

        return page.map(product -> modelMapper.map(product, FilterProductResponse.class));
    }

    private Product buildProductFromForm(Product product, CreateAndUpdateProductForm form) {
        // Validate category
        Category category = categoryRepository.findById(form.getCategoryId())
                .orElseThrow(() -> new NotFoundException(ErrorCodeConstant.CATEGORY_NOT_FOUND_BY_ID,
                        "Không tìm thấy danh mục với ID: " + form.getCategoryId()));

        // Validate price
        if (form.getPrice() == null || form.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(ErrorCodeConstant.INVALID_PRICE, "Giá sản phẩm phải lớn hơn 0");
        }
        if (form.getDiscountPrice() != null &&
                form.getDiscountPrice().compareTo(form.getPrice()) >= 0) {
            throw new BusinessException(ErrorCodeConstant.INVALID_PRICE, "Giá khuyến mãi phải nhỏ hơn giá gốc");
        }

        // Set product fields
        product.setName(form.getName());
        product.setSku(form.getSku());
        product.setDescription(form.getDescription());
        product.setPrice(form.getPrice());
        product.setDiscountPrice(form.getDiscountPrice());
        product.setInStock(true);
        product.setCategory(category);

        // Set images
        if (form.getImageUrls() != null) {
            List<ProductImage> images = form.getImageUrls().stream()
                    .map(url -> {
                        ProductImage img = new ProductImage();
                        img.setImageUrl(url);
                        img.setProduct(product);
                        return img;
                    })
                    .toList();
            product.setImages(images);
        }

        // Set variants
        if (form.getVariant() != null) {
            List<ProductVariant> variants = new ArrayList<>();

            for (ProductVariantRequest variantReq : form.getVariant()) {
                Color color = colorRepository.findById(variantReq.getColorId())
                        .orElseThrow(() -> new NotFoundException(ErrorCodeConstant.COLOR_NOT_FOUND_BY_ID,
                                "Không tìm thấy màu với ID: " + variantReq.getColorId()));

                if (variantReq.getSize() == null) {
                    throw new BusinessException(ErrorCodeConstant.INVALID_SIZE, "Size không được để trống");
                }

                ProductVariant variant = new ProductVariant();
                variant.setProduct(product);
                variant.setColor(color);
                variant.setSize(variantReq.getSize());
                variant.setQuantity(variantReq.getQuantity());
                variant.setVariantImageUrl(variantReq.getColorImageUrl());

                variants.add(variant);
            }

            product.setVariants(variants);
        }

        return product;
    }
}
