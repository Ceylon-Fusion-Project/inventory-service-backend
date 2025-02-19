package com.finalProject.inventry_service.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InventoryAvailabilityRequestDTO {
    private Long orderId;
    private Long productId;
    @Min(value = 1, message = "Requested quantity must be at least 1")
    private Integer requestedQuantity;


    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public @Min(value = 1, message = "Requested quantity must be at least 1") Integer getRequestedQuantity() {
        return requestedQuantity;
    }

    public void setRequestedQuantity(@Min(value = 1, message = "Requested quantity must be at least 1") Integer requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }
}
