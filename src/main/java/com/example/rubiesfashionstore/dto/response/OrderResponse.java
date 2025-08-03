package com.example.rubiesfashionstore.dto.response;

import jdk.jfr.Timespan;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
public class OrderResponse {
    private Integer id;
    private LocalDateTime orderDate;
    private LocalDateTime updatedAt;
    private String shippingAddress;
    private String phoneReceiver;
    private String note;
    private String status;
    private String paymentMethod;
    private BigDecimal totalBeforeDiscount;
    private BigDecimal discountAmount;
    private BigDecimal shippingFee;
    private BigDecimal totalAfterDiscount;
    private List<OrderItemResponse> items;
}
