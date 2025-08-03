package com.example.rubiesfashionstore.form;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class OrderForm {

    @NotBlank(message = "Vui lòng nhập địa chỉ giao hàng")
    private String shippingAddress;

    @NotBlank(message = "Vui lòng nhập số điện thoại người nhận")
    private String phoneReceiver;

    private String note;

    private String couponCode;

    @NotBlank(message = "Vui lòng chọn phương thức thanh toán")
    private String paymentMethod;

    @NotEmpty(message = "Đơn hàng phải có ít nhất 1 sản phẩm")
    private List<OrderItemForm> items;
    public static class OrderItemForm {
        @NotNull(message = "Vui lòng chọn sản phẩm")
        private Integer productId;

        @NotNull(message = "Vui lòng nhập số lượng")
        private Integer quantity;

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

    }
}
