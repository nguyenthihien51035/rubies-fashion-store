package com.example.rubiesfashionstore.service.impl;

import com.example.rubiesfashionstore.dto.response.OrderResponse;
import com.example.rubiesfashionstore.enums.OrderStatus;
import com.example.rubiesfashionstore.exception.BusinessException;
import com.example.rubiesfashionstore.exception.ErrorCodeConstant;
import com.example.rubiesfashionstore.form.OrderForm;
import com.example.rubiesfashionstore.model.Order;
import com.example.rubiesfashionstore.model.OrderItem;
import com.example.rubiesfashionstore.model.Product;
import com.example.rubiesfashionstore.model.User;
import com.example.rubiesfashionstore.repository.OrderRepository;
import com.example.rubiesfashionstore.repository.ProductRepository;
import com.example.rubiesfashionstore.repository.UserRepository;
import com.example.rubiesfashionstore.service.OrderService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public OrderServiceImpl(ProductRepository productRepository, OrderRepository orderRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public OrderResponse createOrder(OrderForm form, User user) {
        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(form.getShippingAddress());
        order.setPhoneReceiver(form.getPhoneReceiver());
        order.setNote(form.getNote());
        order.setPaymentMethod(form.getPaymentMethod());
        order.setStatus(OrderStatus.PENDING);

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (OrderForm.OrderItemForm itemForm : form.getItems()) {
            Product product = productRepository.findById(itemForm.getProductId())
                    .orElseThrow(() -> new BusinessException("Sản phẩm không tồn tại", ErrorCodeConstant.PRODUCT_NOT_FOUND_BY_ID));

            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(itemForm.getQuantity()));

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemForm.getQuantity());
            orderItem.setPrice(product.getPrice());
            orderItem.setTotalPrice(itemTotal);
            orderItem.setOrder(order);

            orderItems.add(orderItem);
            total = total.add(itemTotal);
        }

        // Xử lý mã giảm giá, phí vận chuyển nếu có
        order.setTotalBeforeDiscount(total);
        order.setDiscountAmount(BigDecimal.ZERO); // sau này xử lý coupon
        order.setShippingFee(BigDecimal.valueOf(25000)); // cố định
        order.setTotalAfterDiscount(total.add(order.getShippingFee())); // chưa áp mã giảm

        order.setItems(orderItems);

        Order saved = orderRepository.save(order);
        return modelMapper.map(saved, OrderResponse.class);
    }

    @Override
    public List<OrderResponse> getOrdersByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Không tìm thấy người dùng", ErrorCodeConstant.USER_NOT_FOUND));
        List<Order> orders = orderRepository.findByUser(user);
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderResponse.class))
                .toList();
    }
}
