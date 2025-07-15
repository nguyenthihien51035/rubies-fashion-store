package com.example.rubiesfashionstore.modelmapper;

import com.example.rubiesfashionstore.dto.response.ColorResponse;
import com.example.rubiesfashionstore.model.Color;
import org.springframework.stereotype.Component;

@Component
public class ColorMapper {
    public ColorResponse mapToResponse(Color color) {
        ColorResponse response = new ColorResponse();
        response.setId(color.getId());
        response.setName(color.getName());
        return response;
    }
}
