package com.finalProject.inventry_service.service;

import com.finalProject.inventry_service.dto.InventoryRequestDTO;
import com.finalProject.inventry_service.dto.InventoryResponseDTO;
import com.finalProject.inventry_service.model.Inventory;
import com.finalProject.inventry_service.repo.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public InventoryResponseDTO getInventory(Long productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId);
        if (inventory == null) {
            return null;
        }
        return new InventoryResponseDTO(inventory.getProductId(), inventory.getQuantityInStock(), inventory.getReservedStock());
    }

    @Override
    public InventoryResponseDTO addStock(InventoryRequestDTO requestDto) {
        Inventory inventory = inventoryRepository.findByProductId(requestDto.getProductId());
        if (inventory == null) {
            inventory = new Inventory();
            inventory.setProductId(requestDto.getProductId());
            inventory.setQuantityInStock(requestDto.getQuantity());
        } else {
            inventory.setQuantityInStock(inventory.getQuantityInStock() + requestDto.getQuantity());
        }
        inventory = inventoryRepository.save(inventory);
        return new InventoryResponseDTO(inventory.getProductId(), inventory.getQuantityInStock(), inventory.getReservedStock());
    }

    @Override
    public Boolean reserveStock(InventoryRequestDTO requestDto) {
        Inventory inventory = inventoryRepository.findByProductId(requestDto.getProductId());
        if (inventory != null && inventory.getQuantityInStock() - inventory.getReservedStock() >= requestDto.getQuantity()) {
            inventory.setReservedStock(inventory.getReservedStock() + requestDto.getQuantity());
            inventoryRepository.save(inventory);
            return true;
        }
        return false;
    }

    @Override
    public void releaseStock(InventoryRequestDTO requestDto) {
        Inventory inventory = inventoryRepository.findByProductId(requestDto.getProductId());
        if (inventory != null && inventory.getReservedStock() >= requestDto.getQuantity()) {
            inventory.setReservedStock(inventory.getReservedStock() - requestDto.getQuantity());
            inventoryRepository.save(inventory);
        }
    }
}
