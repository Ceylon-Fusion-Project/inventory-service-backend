package com.finalProject.inventry_service.service;

import com.finalProject.inventry_service.dto.*;
import com.finalProject.inventry_service.enums.OrderStatus;
import com.finalProject.inventry_service.model.Inventory;
import com.finalProject.inventry_service.model.ReleaseInventory;
import com.finalProject.inventry_service.repo.InventoryRepository;
import com.finalProject.inventry_service.repo.ReleaseInventoryRepository;
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
    private ReleaseInventoryRepository releaseInventoryRepository;

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

    @Transactional
    @Override
    public InventoryReleaseResponseDTO releaseStock(InventoryReleaseRequestDTO requestDTO) {
        if (requestDTO.getOrderStatus() != OrderStatus.CONFIRMED) {
            throw new IllegalStateException("Order status is not confirmed, cannot release stock.");
        }

        // Fetch inventory based on product ID
        Inventory inventory = inventoryRepository.findByProductId(requestDTO.getProductId());

        if (inventory == null) {
            throw new IllegalArgumentException("Inventory not found for product ID: " + requestDTO.getProductId());
        }

        // Ensure enough stock is available
        if (inventory.getQuantityInStock() < requestDTO.getOrderItemQuantity()) {
            throw new IllegalStateException("Insufficient stock for product ID: " + requestDTO.getProductId());
        }

        // Reduce stock from inventory
        inventory.setQuantityInStock(inventory.getQuantityInStock() - requestDTO.getOrderItemQuantity());
        inventoryRepository.save(inventory);

        // Create and save ReleaseInventory record
        ReleaseInventory releaseInventory = new ReleaseInventory();
        releaseInventory.setProductId(requestDTO.getProductId());
        releaseInventory.setQuantityReleased(requestDTO.getOrderItemQuantity());
        releaseInventory.setInventory(inventory);

        releaseInventory = releaseInventoryRepository.save(releaseInventory);

        // Explicit ModelMapper mapping to avoid ambiguity
        InventoryReleaseResponseDTO responseDTO = new InventoryReleaseResponseDTO();
        responseDTO.setInventoryReleaseId(releaseInventory.getInventoryReleaseId());
        responseDTO.setProductId(releaseInventory.getProductId());
        responseDTO.setQuantityReleased(releaseInventory.getQuantityReleased());
        responseDTO.setInventoryId(releaseInventory.getInventory().getInventoryId()); // Explicit mapping

        return responseDTO;
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

    @Override
    @Transactional
    public String checkInventoryAvailability(CheckInventoryDTO checkInventoryDTO) {
        // Validate productId and requestedQuantity
        if (checkInventoryDTO.getProductId() == null || checkInventoryDTO.getRequestedQuantity() <= 0) {
            throw new IllegalArgumentException("Invalid productId or requestedQuantity");
        }

        // Fetch the inventory for the given productId
        Inventory inventory = inventoryRepository.findByProductId(checkInventoryDTO.getProductId());
        if (inventory == null || inventory.getQuantityInStock() == null) {
            throw new IllegalArgumentException("Inventory not found");
        }

        // Check if the requested quantity is available
        if (inventory.getQuantityInStock() >= checkInventoryDTO.getRequestedQuantity()) {
            return "Product is available. Requested quantity: " + checkInventoryDTO.getRequestedQuantity();
        } else {
            return "Insufficient stock. Available quantity: " + inventory.getQuantityInStock();
        }
    }

}
