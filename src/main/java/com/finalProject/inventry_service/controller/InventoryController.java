package com.finalProject.inventry_service.controller;

import com.finalProject.inventry_service.dto.*;
import com.finalProject.inventry_service.service.InventoryService;
import com.finalProject.inventry_service.util.JwtUtil;
import com.finalProject.inventry_service.util.StandardResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private JwtUtil jwtUtil;

    // Get product inventory by productId
    @PreAuthorize("hasRole('USER')")
    @GetMapping(path = "/product-quantity-by-product-id", params = "productId")
    public ResponseEntity<StandardResponse> getInventory(@RequestParam(value = "productId") Long productId, @RequestHeader("Authorization") String authorizationHeader, Authentication authentication) {
        String token = authorizationHeader.replace("Bearer ", "");
        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new StandardResponse(HttpStatus.UNAUTHORIZED.value(), "Invalid JWT token", null));
        }

        try {
            InventoryResponseDTO responseDto = inventoryService.getInventory(productId);
            StandardResponse standardResponse = new StandardResponse(HttpStatus.OK.value(), "Inventory fetched successfully", responseDto);
            return ResponseEntity.ok(standardResponse);
        } catch (IllegalArgumentException ex) {
            StandardResponse standardResponse = new StandardResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
            return ResponseEntity.badRequest().body(standardResponse);
        } catch (Exception e) {
            StandardResponse standardResponse = new StandardResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(standardResponse);
        }
    }

    // Add stock to inventory
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/products/add-stock")
    public ResponseEntity<StandardResponse> addStock(@Valid @RequestBody InventoryRequestDTO requestDto) {
        try {
            InventoryResponseDTO responseDto = inventoryService.addStock(requestDto);
            StandardResponse standardResponse = new StandardResponse(HttpStatus.CREATED.value(), "Inventory added successfully", responseDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(standardResponse);
        } catch (IllegalArgumentException ex) {
            StandardResponse standardResponse = new StandardResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
            return ResponseEntity.badRequest().body(standardResponse);
        } catch (Exception e) {
            StandardResponse standardResponse = new StandardResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(standardResponse);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "products/release-stock")
    public ResponseEntity<StandardResponse> releaseStock(@Valid @RequestBody InventoryReleaseRequestDTO requestDTO) {
        try {
            InventoryReleaseResponseDTO responseDTO = inventoryService.releaseStock(requestDTO);
            StandardResponse standardResponse = new StandardResponse(HttpStatus.CREATED.value(), "Inventory released successfully", responseDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(standardResponse);
        } catch (IllegalArgumentException ex) {
            StandardResponse standardResponse = new StandardResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
            return ResponseEntity.badRequest().body(standardResponse);
        } catch (Exception e) {
            StandardResponse standardResponse = new StandardResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(standardResponse);
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(path = "get-all-products-quantity", params = {"page", "size"})
    public ResponseEntity<StandardResponse> getAllInventory(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestHeader("Authorization") String authorizationHeader, Authentication authentication) {
        String token = authorizationHeader.replace("Bearer ", "");
        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new StandardResponse(HttpStatus.UNAUTHORIZED.value(), "Invalid JWT token", null));
        }

        try {
            Pageable pageable = PageRequest.of(page, size);
            List<InventoryResponseDTO> inventories = inventoryService.getAllInventory(pageable);
            StandardResponse standardResponse = new StandardResponse(HttpStatus.OK.value(), "All inventories fetch successfully", inventories);
            return ResponseEntity.ok(standardResponse);
        } catch (IllegalArgumentException ex) {
            StandardResponse standardResponse = new StandardResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
            return ResponseEntity.badRequest().body(standardResponse);
        } catch (Exception e) {
            StandardResponse standardResponse = new StandardResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(standardResponse);
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/check-availability")
    public ResponseEntity<StandardResponse> checkInventoryAvailability(@Valid @RequestBody InventoryAvailabilityRequestDTO requestDTO, @RequestHeader("Authorization") String authorizationHeader, Authentication authentication) {
        String token = authorizationHeader.replace("Bearer ", "");
        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new StandardResponse(HttpStatus.UNAUTHORIZED.value(), "Invalid JWT token", null));
        }

        try {
            InventoryAvailabilityResponseDTO responseDTO = inventoryService.checkInventoryAvailability(requestDTO);
            StandardResponse standardResponse = new StandardResponse(HttpStatus.OK.value(), "Checked successfully", responseDTO);
            return ResponseEntity.ok(standardResponse);
        } catch (IllegalArgumentException ex) {
            StandardResponse standardResponse = new StandardResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
            return ResponseEntity.badRequest().body(standardResponse);
        } catch (Exception e) {
            StandardResponse standardResponse = new StandardResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(standardResponse);
        }
    }
}