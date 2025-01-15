package com.finalProject.inventry_service.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InventoryRequestDTO {
    private Long productId;

    @Min(0)
    private Integer quantity;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public @Min(0) Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(@Min(0) Integer quantity) {
        this.quantity = quantity;
    }
}
