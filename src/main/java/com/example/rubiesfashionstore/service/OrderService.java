package com.example.rubiesfashionstore.service;

import com.example.rubiesfashionstore.dto.response.OrderResponse;

import com.example.rubiesfashionstore.form.OrderForm;
import com.example.rubiesfashionstore.model.User;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderForm form, User user);
    List<OrderResponse> getOrdersByEmail(String email);

}
