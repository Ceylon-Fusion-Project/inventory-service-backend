package com.finalProject.inventry_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class InventoryResponseDTO {
    private Long productId;
    private Integer quantityInStock;
    private Integer reservedStock;

    public InventoryResponseDTO(Long productId, Integer quantityInStock, Integer reservedStock) {
        this.productId = productId;
        this.quantityInStock = quantityInStock;
        this.reservedStock = reservedStock;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(Integer quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public Integer getReservedStock() {
        return reservedStock;
    }

    public void setReservedStock(Integer reservedStock) {
        this.reservedStock = reservedStock;
    }
}
