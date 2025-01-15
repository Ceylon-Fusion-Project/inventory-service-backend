package com.finalProject.inventry_service.service;

import com.finalProject.inventry_service.dto.InventoryRequestDTO;
import com.finalProject.inventry_service.dto.InventoryResponseDTO;

public interface InventoryService {
    InventoryResponseDTO getInventory(Long productId);
    InventoryResponseDTO addStock(InventoryRequestDTO inventoryrequestDTO);
    Boolean reserveStock(InventoryRequestDTO inventoryRequestDTO);
    void releaseStock(InventoryRequestDTO inventoryRequestDTO);
}
