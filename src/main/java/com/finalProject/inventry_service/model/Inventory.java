package com.finalProject.inventry_service.model;

import com.finalProject.inventry_service.enums.ProductStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id",nullable = false)
    private Long inventoryId;
    @Column(name = "product_id",nullable = false)
    private Long productId;
    @Min(0)
    @Column(name = "quantity_in_stock",nullable = false)
    private Integer quantityInStock;
    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private ProductStatus status;
    @OneToMany(mappedBy = "inventory",cascade = CascadeType.ALL)
    private List<InventoryStockHold> inventoryStockHold;

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public @Min(0) Integer getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(@Min(0) Integer quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public ProductStatus getStatus() {
        return status;
    }

    @PrePersist
    @PreUpdate
    private void updateStatus() {
       if(quantityInStock > 0){
           this.status = ProductStatus.ACTIVE;
       }else{
           this.status = ProductStatus.INACTIVE;
       }
    }
}