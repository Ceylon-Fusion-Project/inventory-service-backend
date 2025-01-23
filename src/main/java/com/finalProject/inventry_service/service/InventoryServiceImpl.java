package com.finalProject.inventry_service.service;

import com.finalProject.inventry_service.dto.InventoryRequestDTO;
import com.finalProject.inventry_service.dto.InventoryResponseDTO;
import com.finalProject.inventry_service.model.Inventory;
import com.finalProject.inventry_service.repo.InventoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public InventoryResponseDTO getInventory(Long productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId);
        if (inventory == null) {
            throw new IllegalArgumentException("Inventory not found" + productId);
        }
        // Use ModelMapper to map entity to DTO
        return modelMapper.map(inventory, InventoryResponseDTO.class);
    }

    @Override
    public InventoryResponseDTO addStock(InventoryRequestDTO requestDto) {
        Inventory inventory = inventoryRepository.findByProductId(requestDto.getProductId());

        if (requestDto.getQuantityInStock() == null || requestDto.getQuantityInStock() < 0) {
            throw new IllegalArgumentException("Invalid quantity");
        }

        if (inventory == null) {
            inventory = new Inventory();
            inventory.setProductId(requestDto.getProductId());
            inventory.setQuantityInStock(requestDto.getQuantityInStock());
        } else {
            inventory.setQuantityInStock(inventory.getQuantityInStock() + requestDto.getQuantityInStock());
        }
        inventory = inventoryRepository.save(inventory);
        // Use ModelMapper to map entity to DTO
        return modelMapper.map(inventory, InventoryResponseDTO.class);
    }

}
