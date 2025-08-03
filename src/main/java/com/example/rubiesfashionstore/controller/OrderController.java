package com.example.rubiesfashionstore.controller;

import com.example.rubiesfashionstore.dto.ApiResponse;
import com.example.rubiesfashionstore.dto.response.OrderResponse;
import com.example.rubiesfashionstore.form.OrderForm;
import com.example.rubiesfashionstore.security.CustomUserDetails;
import com.example.rubiesfashionstore.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse> createOrder(@Valid @RequestBody OrderForm form, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        OrderResponse response = orderService.createOrder(form, userDetails.getUser());
        return ResponseEntity.ok(new ApiResponse("Tạo đơn hàng thành công", response));
    }

    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    @GetMapping("/my-orders")
    public ResponseEntity<ApiResponse> getMyOrders(Authentication authentication) {
        String email = authentication.getName();
        List<OrderResponse> orders = orderService.getOrdersByEmail(email);
        return ResponseEntity.ok(new ApiResponse("Lấy danh sách đơn hàng thành công", orders));
    }

}
