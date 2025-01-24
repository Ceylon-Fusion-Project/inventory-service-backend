package com.finalProject.inventry_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InventoryReleaseResponseDTO {
    private Long inventoryReleaseId;
    private Long productId;
    private Integer quantityReleased;
    private Long inventoryId;

    public Long getInventoryReleaseId() {
        return inventoryReleaseId;
    }

    public void setInventoryReleaseId(Long inventoryReleaseId) {
        this.inventoryReleaseId = inventoryReleaseId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantityReleased() {
        return quantityReleased;
    }

    public void setQuantityReleased(Integer quantityReleased) {
        this.quantityReleased = quantityReleased;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }
}
