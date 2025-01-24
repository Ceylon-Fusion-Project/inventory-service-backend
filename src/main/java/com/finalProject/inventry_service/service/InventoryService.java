package com.finalProject.inventry_service.service;

import com.finalProject.inventry_service.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InventoryService {
    InventoryResponseDTO getInventory(Long productId);
    InventoryResponseDTO addStock(InventoryRequestDTO inventoryrequestDTO);
    List<InventoryResponseDTO> getAllInventory(Pageable pageable);
    String checkInventoryAvailability(CheckInventoryDTO checkInventoryDTO);
    InventoryReleaseResponseDTO releaseStock(InventoryReleaseRequestDTO requestDTO);
}
