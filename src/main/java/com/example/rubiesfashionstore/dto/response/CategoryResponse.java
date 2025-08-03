package com.example.rubiesfashionstore.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class CategoryResponse {
    private Integer id;
    private String name;
    private String image;
}
