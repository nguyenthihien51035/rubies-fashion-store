package com.example.rubiesfashionstore.modelmapper;

import com.example.rubiesfashionstore.dto.response.FilterProductResponse;
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

        return mapper;
    }
}
