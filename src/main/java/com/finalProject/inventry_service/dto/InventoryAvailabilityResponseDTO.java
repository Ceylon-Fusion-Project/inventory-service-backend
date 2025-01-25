package com.finalProject.inventry_service.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class InventoryAvailabilityResponseDTO {
    private Boolean available;
    @Min(value = 1, message = "Available quantity must be at least 1")
    private Integer availableQuantity;
    private String message;

    public InventoryAvailabilityResponseDTO(Boolean available, Integer availableQuantity, String message) {
        this.available = available;
        this.availableQuantity = availableQuantity;
        this.message = message;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public @Min(value = 1, message = "Available quantity must be at least 1") Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(@Min(value = 1, message = "Available quantity must be at least 1") Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
