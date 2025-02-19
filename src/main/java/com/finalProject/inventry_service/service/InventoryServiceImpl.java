package com.finalProject.inventry_service.service;

import com.finalProject.inventry_service.dto.*;
import com.finalProject.inventry_service.model.Inventory;
import com.finalProject.inventry_service.model.InventoryStockHold;
import com.finalProject.inventry_service.model.OrderInventory;
import com.finalProject.inventry_service.repo.InventoryRepository;
import com.finalProject.inventry_service.repo.InventoryStockHoldRepository;
import com.finalProject.inventry_service.repo.OrderInventoryRepository;
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
    private InventoryStockHoldRepository inventoryStockHoldRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrderInventoryRepository orderInventoryRepository;

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
    public InventoryResponseDTO addStock(Long productId, Integer quantityInStock) {
        Inventory inventory = inventoryRepository.findByProductId(productId);

        if (quantityInStock == null || quantityInStock < 0) {
            throw new IllegalArgumentException("Invalid quantity");
        }

        if (inventory == null) {
            inventory = new Inventory();
            inventory.setProductId(productId);
            inventory.setQuantityInStock(quantityInStock);
        } else {
            inventory.setQuantityInStock(inventory.getQuantityInStock() + quantityInStock);
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

    @Override
    @Transactional
    public InventoryAvailabilityResponseDTO checkAvailability(InventoryAvailabilityRequestDTO requestDTO) {
        Long productId = requestDTO.getProductId();
        Integer requestedQuantity = requestDTO.getRequestedQuantity();
        Long orderId = requestDTO.getOrderId(); // Order ID added to track the request

        // Find the inventory for the product
        Inventory inventory = inventoryRepository.findByProductId(productId);
        InventoryAvailabilityResponseDTO response = new InventoryAvailabilityResponseDTO();

        // If inventory is not found
        if (inventory == null) {
            response.setMessage("Inventory not found for product ID: " + productId);
            return response;
        }

        // Check if there is sufficient inventory
        if (inventory.getQuantityInStock() < requestedQuantity) {
            response.setMessage("Insufficient stock. Available: " + inventory.getQuantityInStock() +
                    ", Requested: " + requestedQuantity);
            return response;
        }

        // Create a new stock hold record for this request
        InventoryStockHold stockHold = new InventoryStockHold();
        stockHold.setProductId(productId);
        stockHold.setQuantityHold(requestedQuantity);
        stockHold.setOrderId(orderId); // Set the order ID for the stock hold
        stockHold.setInventory(inventory);

        // Reduce available quantity in inventory
        inventory.setQuantityInStock(inventory.getQuantityInStock() - requestedQuantity);

        // Save new stock hold record and update inventory
        inventoryStockHoldRepository.save(stockHold);
        inventoryRepository.save(inventory);

        // Prepare success response
        response.setMessage("Stock hold created successfully. Inventory updated.");
        return response;
    }

    @Override
    @Transactional
    public ConfirmOrderResponseDTO confirmOrder(ConfirmOrderRequestDTO requestDTO) {
        Long orderId = requestDTO.getOrderId();
        Long productId = requestDTO.getProductId();
        Integer orderQuantity = requestDTO.getOrderQuantity();

        // Fetch the stock hold related to the order and product
        InventoryStockHold stockHold = inventoryStockHoldRepository.findByOrderIdAndProductId(orderId, productId);

        // Create the response DTO
        ConfirmOrderResponseDTO response = new ConfirmOrderResponseDTO();

        // If no stock hold found, return message indicating the stock hold is not present
        if (stockHold == null) {
            response.setMessage("No inventory hold found for order ID: " + orderId + " and product ID: " + productId);
            return response;
        }

        // Check if the order quantity matches the stock hold quantity
        if (stockHold.getQuantityHold() != orderQuantity) {
            response.setMessage("Mismatch in order quantity. Stock hold quantity: " + stockHold.getQuantityHold() +
                    ", Order quantity: " + orderQuantity);
            return response;
        }

        // Save the stock hold details to the OrderInventory table
        OrderInventory orderInventory = new OrderInventory();
        orderInventory.setProductId(productId);
        orderInventory.setOrderQuentity(orderQuantity); // Set order quantity
        orderInventory.setOrderId(orderId);
        orderInventory = orderInventoryRepository.save(orderInventory); // Save to DB

        // Update the inventory quantity: reduce by the order quantity
        Inventory inventory = stockHold.getInventory();
        inventory.setQuantityInStock(inventory.getQuantityInStock() - orderQuantity);
        inventoryRepository.save(inventory); // Save updated inventory

        // Clear the stock hold after confirming the order
        inventoryStockHoldRepository.delete(stockHold);

        // Set the response message and orderInventoryId
        response.setMessage("Order confirmed and inventory updated successfully.");
        response.setOrderInventoryId(orderInventory.getOrderInventoryId());

        return response;
    }

    @Override
    @Transactional
    public CancelOrderResponseDTO cancelOrder(CancelOrderRequestDTO requestDTO) {
        Long orderId = requestDTO.getOrderId();
        Long productId = requestDTO.getProductId();
        Integer orderQuantity = requestDTO.getOrderQuantity();

        // Fetch the stock hold related to the order and product
        InventoryStockHold stockHold = inventoryStockHoldRepository.findByOrderIdAndProductId(orderId, productId);

        // Create the response DTO
        CancelOrderResponseDTO response = new CancelOrderResponseDTO();

        // If no stock hold found, return message indicating the stock hold is not present
        if (stockHold == null) {
            response.setMessage("No inventory hold found for order ID: " + orderId + " and product ID: " + productId);
            return response;
        }

        // Check if the order quantity matches the stock hold quantity
        if (stockHold.getQuantityHold() != orderQuantity) {
            response.setMessage("Mismatch in order quantity. Stock hold quantity: " + stockHold.getQuantityHold() +
                    ", Order quantity: " + orderQuantity);
            return response;
        }

        // Add the held quantity back to the inventory
        Inventory inventory = stockHold.getInventory();
        inventory.setQuantityInStock(inventory.getQuantityInStock() + orderQuantity);
        inventoryRepository.save(inventory); // Save updated inventory

        // Clear the stock hold after cancelling the order
        inventoryStockHoldRepository.delete(stockHold);

        // Set the response message
        response.setMessage("Order cancelled and inventory restocked successfully.");

        return response;
    }
}
