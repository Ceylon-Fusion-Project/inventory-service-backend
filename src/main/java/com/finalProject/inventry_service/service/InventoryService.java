package com.finalProject.inventry_service.service;

import com.finalProject.inventry_service.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InventoryService {
    InventoryResponseDTO getInventory(Long productId);
    InventoryResponseDTO addStock(Long productId, Integer quantityInStock);
    List<InventoryResponseDTO> getAllInventory(Pageable pageable);
    InventoryAvailabilityResponseDTO checkInventoryAvailability(InventoryAvailabilityRequestDTO requestDTO);
    InventoryStockHoldResponseDTO holdStock(InventoryStockHoldRequestDTO requestDTO);
}
