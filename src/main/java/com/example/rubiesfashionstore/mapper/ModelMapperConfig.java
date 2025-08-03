package com.example.rubiesfashionstore.mapper;

import com.example.rubiesfashionstore.dto.response.FilterProductResponse;
import com.example.rubiesfashionstore.dto.response.OrderItemResponse;
import com.example.rubiesfashionstore.dto.response.OrderResponse;
import com.example.rubiesfashionstore.model.OrderItem;
import com.example.rubiesfashionstore.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        // Mapping từ Product → FilterProductResponse
        mapper.typeMap(Product.class, FilterProductResponse.class)
                .addMapping(
                        src -> src.getCategory().getName(), // nguồn: product.getCategory().getName()
                        FilterProductResponse::setCategoryName // đích: dto.setCategoryName(...)
                );

        mapper.typeMap(OrderItem.class, OrderItemResponse.class)
                .addMapping(src -> src.getProduct().getId(), OrderItemResponse::setProductId)
                .addMapping(src -> src.getProduct().getName(), OrderItemResponse::setProductName);


        return mapper;
    }
}
