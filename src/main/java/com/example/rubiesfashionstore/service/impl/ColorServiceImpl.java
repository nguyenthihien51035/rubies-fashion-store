package com.example.rubiesfashionstore.service.impl;

import com.example.rubiesfashionstore.dto.response.ColorResponse;
import com.example.rubiesfashionstore.exception.BusinessException;
import com.example.rubiesfashionstore.exception.ErrorCodeConstant;
import com.example.rubiesfashionstore.exception.NotFoundException;
import com.example.rubiesfashionstore.form.product.ColorForm;
import com.example.rubiesfashionstore.model.Color;
import com.example.rubiesfashionstore.modelmapper.ColorMapper;
import com.example.rubiesfashionstore.repository.ColorRepository;
import com.example.rubiesfashionstore.service.ColorService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ColorServiceImpl implements ColorService {
    public final ColorRepository colorRepository;
    public final ColorMapper colorMapper;

    public ColorServiceImpl(ColorRepository colorRepository, ColorMapper colorMapper) {
        this.colorRepository = colorRepository;
        this.colorMapper = colorMapper;
    }

    @Override
    @Transactional
    public ColorResponse createColor(ColorForm form) {
        if(colorRepository.existsByName(form.getName())) {
        throw new BusinessException("Màu đã tồn tại", ErrorCodeConstant.COLOR_NAME_ALREADY_EXISTS);
        }
        Color color = new Color();
        color.setName(form.getName());
        Color saved = colorRepository.save(color);
        return colorMapper.mapToResponse(saved);
    }

    @Override
    public ColorResponse updateColor(Integer id, ColorForm form) {
        Color color = colorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy màu trong hệ thống", ErrorCodeConstant.COLOR_NOT_FOUND_BY_ID));

        if(colorRepository.existsByName(form.getName())) {
            throw new BusinessException("Màu đã tồn tại", ErrorCodeConstant.COLOR_NAME_ALREADY_EXISTS);
        }

        color.setName(form.getName());
        Color saved = colorRepository.save(color);
        return colorMapper.mapToResponse(saved);
    }

    @Override
    public void deleteColor(Integer id) {
        Color color = colorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy trong hệ thống", ErrorCodeConstant.COLOR_NOT_FOUND_BY_ID));
        colorRepository.deleteById(id);
    }

    @Override
    public ColorResponse getColorById(Integer id) {
        Color color = colorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy trong hệ thống", ErrorCodeConstant.COLOR_NOT_FOUND_BY_ID));
        return colorMapper.mapToResponse(color);
    }

    @Override
    public List<ColorResponse> getAllColor() {
        return colorRepository.findAll()
                .stream()
                .map(colorMapper::mapToResponse)
                .collect(Collectors.toList());
    }
}
