package com.finalProject.inventry_service.dto;

import com.finalProject.inventry_service.enums.OrderStatus;

public class OrderInventoryRequestDTO {
    private Long orderId;
    private Long productId;
    private Integer orderItemQuantity;
    private OrderStatus orderStatus;
}
