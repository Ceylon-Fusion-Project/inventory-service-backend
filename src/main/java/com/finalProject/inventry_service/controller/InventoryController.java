package com.finalProject.inventry_service.controller;

import com.finalProject.inventry_service.dto.InventoryRequestDTO;
import com.finalProject.inventry_service.dto.InventoryResponseDTO;
import com.finalProject.inventry_service.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    // Get product inventory by productId
    @GetMapping("/products/{productId}")
    public ResponseEntity<InventoryResponseDTO> getInventory(@PathVariable Long productId) {
        InventoryResponseDTO responseDto = inventoryService.getInventory(productId);
        if (responseDto != null) {
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Add stock to inventory
    @PostMapping("/products/add-stock")
    public ResponseEntity<InventoryResponseDTO> addStock(@Valid @RequestBody InventoryRequestDTO requestDto) {
        InventoryResponseDTO responseDto = inventoryService.addStock(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // Reserve stock
    @PostMapping("/products/reserve")
    public ResponseEntity<String> reserveStock(@Valid @RequestBody InventoryRequestDTO requestDto) {
        boolean success = inventoryService.reserveStock(requestDto);
        if (success) {
            return new ResponseEntity<>("Stock reserved successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Insufficient stock", HttpStatus.BAD_REQUEST);
    }

    // Release reserved stock
    @PostMapping("/products/release")
    public ResponseEntity<String> releaseStock(@Valid @RequestBody InventoryRequestDTO requestDto) {
        inventoryService.releaseStock(requestDto);
        return new ResponseEntity<>("Stock released successfully", HttpStatus.OK);
    }
}
