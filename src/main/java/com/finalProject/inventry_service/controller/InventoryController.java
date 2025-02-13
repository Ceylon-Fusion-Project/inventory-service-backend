package com.finalProject.inventry_service.controller;

import com.finalProject.inventry_service.dto.*;
import com.finalProject.inventry_service.service.InventoryService;
import com.finalProject.inventry_service.util.StandardResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/V1/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    // Helper method to log user details
    private void logUserAccess(String userId, String role) {
        System.out.println("Accessed by User ID: " + userId + ", Role: " + role);
    }

    // Helper method to check admin access
    private ResponseEntity<StandardResponse> checkAdminAccess(String role) {
        if (!"admin".equalsIgnoreCase(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new StandardResponse(HttpStatus.FORBIDDEN.value(), "Access denied: Admins only", null));
        }
        return null;
    }

    @GetMapping(path = "/product-quantity-by-product-id", params = "productId")
    public ResponseEntity<StandardResponse> getInventory(
            @RequestParam(value = "productId") Long productId,
            @RequestHeader("X-User-Id") String userId,
            @RequestHeader("X-User-Role") String role) {

        logUserAccess(userId, role);
        ResponseEntity<StandardResponse> accessDenied = checkAdminAccess(role);
        if (accessDenied != null) return accessDenied;

        try {
            InventoryResponseDTO responseDto = inventoryService.getInventory(productId);
            return ResponseEntity.ok(new StandardResponse(HttpStatus.OK.value(), "Inventory fetched successfully", responseDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new StandardResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null));
        }
    }

    @PostMapping(path = "/products/add-stock")
    public ResponseEntity<StandardResponse> addStock(
            @RequestParam(value = "productId") Long productId,
            @RequestParam(value = "quantityInStock") Integer quantityInStock,
            @RequestHeader("X-User-Id") String userId,
            @RequestHeader("X-User-Role") String role) {

        logUserAccess(userId, role);
        ResponseEntity<StandardResponse> accessDenied = checkAdminAccess(role);
        if (accessDenied != null) return accessDenied;

        try {
            InventoryResponseDTO responseDto = inventoryService.addStock(productId, quantityInStock);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new StandardResponse(HttpStatus.CREATED.value(), "Inventory added successfully", responseDto));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest()
                    .body(new StandardResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new StandardResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null));
        }
    }

//    @PostMapping(path = "/products/release-stock")
//    public ResponseEntity<StandardResponse> releaseStock(
//            @Valid @RequestBody InventoryReleaseRequestDTO requestDTO,
//            @RequestHeader("X-User-Id") String userId,
//            @RequestHeader("X-User-Role") String role) {
//
//        logUserAccess(userId, role);
//        ResponseEntity<StandardResponse> accessDenied = checkAdminAccess(role);
//        if (accessDenied != null) return accessDenied;
//
//        try {
//            InventoryReleaseResponseDTO responseDTO = inventoryService.releaseStock(requestDTO);
//            return ResponseEntity.status(HttpStatus.CREATED)
//                    .body(new StandardResponse(HttpStatus.CREATED.value(), "Inventory released successfully", responseDTO));
//        } catch (IllegalArgumentException ex) {
//            return ResponseEntity.badRequest()
//                    .body(new StandardResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new StandardResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null));
//        }
//    }

    @GetMapping(path = "/get-all-products-quantity", params = {"page", "size"})
    public ResponseEntity<StandardResponse> getAllInventory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestHeader("X-User-Id") String userId,
            @RequestHeader("X-User-Role") String role) {

        logUserAccess(userId, role);
        ResponseEntity<StandardResponse> accessDenied = checkAdminAccess(role);
        if (accessDenied != null) return accessDenied;

        try {
            Pageable pageable = PageRequest.of(page, size);
            List<InventoryResponseDTO> inventories = inventoryService.getAllInventory(pageable);
            return ResponseEntity.ok(new StandardResponse(HttpStatus.OK.value(), "All inventories fetched successfully", inventories));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest()
                    .body(new StandardResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new StandardResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null));
        }
    }

    @PostMapping("/check-availability")
    public ResponseEntity<StandardResponse> checkInventoryAvailability(
            @Valid @RequestBody InventoryAvailabilityRequestDTO requestDTO,
            @RequestHeader("X-User-Id") String userId,
            @RequestHeader("X-User-Role") String role) {

        logUserAccess(userId, role);
        ResponseEntity<StandardResponse> accessDenied = checkAdminAccess(role);
        if (accessDenied != null) return accessDenied;

        try {
            InventoryAvailabilityResponseDTO responseDTO = inventoryService.checkInventoryAvailability(requestDTO);
            return ResponseEntity.ok(new StandardResponse(HttpStatus.OK.value(), "Checked successfully", responseDTO));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest()
                    .body(new StandardResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new StandardResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null));
        }
    }
}
