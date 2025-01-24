package com.finalProject.inventry_service.controller;

import com.finalProject.inventry_service.dto.InventoryRequestDTO;
import com.finalProject.inventry_service.dto.InventoryResponseDTO;
import com.finalProject.inventry_service.service.InventoryService;
import com.finalProject.inventry_service.util.StandardResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    // Get product inventory by productId
    @GetMapping(
            path = "/product-quantity-by-product-id",
            params = "productId")
    public ResponseEntity<StandardResponse> getInventory(@RequestParam(value = "productId") Long productId) {
        try {
            InventoryResponseDTO responseDto = inventoryService.getInventory(productId);
            StandardResponse standardResponse = new StandardResponse(HttpStatus.OK.value(), "Inventory fetched successfully", responseDto);
            return ResponseEntity.ok(standardResponse);
        } catch (IllegalArgumentException e) {
            StandardResponse standardResponse = new StandardResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return ResponseEntity.badRequest().body(standardResponse);
        } catch (Exception e) {
            StandardResponse standardResponse = new StandardResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(standardResponse);
        }
    }

    // Add stock to inventory
    @PostMapping(
            path = "/products/add-stock")
    public ResponseEntity<StandardResponse> addStock(@Valid @RequestBody InventoryRequestDTO requestDto) {
        try {
            InventoryResponseDTO responseDto = inventoryService.addStock(requestDto);
            StandardResponse standardResponse = new StandardResponse(HttpStatus.CREATED.value(), "Inventory added successfully", responseDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(standardResponse);
        } catch (IllegalArgumentException ex) {
            StandardResponse standardResponse = new StandardResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
            return ResponseEntity.badRequest().body(standardResponse);
        } catch (Exception e) {
            StandardResponse standardResponse = new StandardResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(standardResponse);
        }
    }
    @GetMapping(
            path = "get-all-products-quantity",
            params = {"page", "size"}
    )
    public ResponseEntity<StandardResponse> getAllInventory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        try {
            Pageable pageable = PageRequest.of(page, size);
            List<InventoryResponseDTO> inventories = inventoryService.getAllInventory(pageable);
            StandardResponse standardResponse = new StandardResponse(HttpStatus.OK.value(), "All inventories fetch successfully", inventories);
            return ResponseEntity.ok(standardResponse);
        }catch (IllegalArgumentException ex) {
            StandardResponse standardResponse = new StandardResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
            return ResponseEntity.badRequest().body(standardResponse);
        }
        catch (Exception e){
            StandardResponse standardResponse = new StandardResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occured", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(standardResponse);
        }
    }
}
