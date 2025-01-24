package com.finalProject.inventry_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CheckInventoryDTO {
    private Long productId;
    private Integer requestedQuantity;

    public CheckInventoryDTO( Long productId, Integer requestedQuantity) {

        this.productId = productId;
        this.requestedQuantity = requestedQuantity;
    }


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getRequestedQuantity() {
        return requestedQuantity;
    }

    public void setRequestedQuantity(Integer requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }
}
