package com.finalProject.inventry_service.service;

import com.finalProject.inventry_service.dto.InventoryRequestDTO;
import com.finalProject.inventry_service.dto.InventoryResponseDTO;
import com.finalProject.inventry_service.model.Inventory;
import com.finalProject.inventry_service.repo.InventoryRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public InventoryResponseDTO getInventory(Long productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId);
        if (inventory == null) {
            throw new IllegalArgumentException("Inventory not found" + productId);
        }
        // Use ModelMapper to map entity to DTO
        return modelMapper.map(inventory, InventoryResponseDTO.class);
    }

    @Override
    @Transactional
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

    @Override
    @Transactional
    public List<InventoryResponseDTO> getAllInventory(Pageable pageable) {
        Page<Inventory> inventories = inventoryRepository.findAll(pageable);
        if (inventories == null || inventories.isEmpty()) {
            throw new IllegalArgumentException("Inventories not found");
        }
        return inventories.getContent()
                .stream()
                .map(inventory -> modelMapper.map(inventory, InventoryResponseDTO.class))
                .toList();
    }

}
