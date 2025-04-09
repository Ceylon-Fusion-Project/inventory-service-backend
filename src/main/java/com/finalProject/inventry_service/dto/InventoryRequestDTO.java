package com.finalProject.inventry_service.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InventoryRequestDTO {
    private Long orderId;
    private Long productId;
    @Min(0)
    private Integer quantityInStock;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public @Min(0) Integer getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(@Min(0) Integer quantityInStock) {
        this.quantityInStock = quantityInStock;
    }
}
