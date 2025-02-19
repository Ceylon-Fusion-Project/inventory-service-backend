package com.finalProject.inventry_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConfirmOrderResponseDTO {
    private Long orderInventoryId;
    private String message;

    public Long getOrderInventoryId() {
        return orderInventoryId;
    }

    public void setOrderInventoryId(Long orderInventoryId) {
        this.orderInventoryId = orderInventoryId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
