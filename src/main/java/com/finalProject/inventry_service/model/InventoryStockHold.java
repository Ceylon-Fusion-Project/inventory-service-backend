package com.finalProject.inventry_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "inventory_stock_hold")
public class InventoryStockHold {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_stock_hold_id",nullable = false)
    private Long inventoryStockHoldId;
    @Column(name = "product_id",nullable = false)
    private Long productId;
    @Column(name = "quantity_hold",nullable = false)
    private Integer quantityHold;
    @ManyToOne
    @JoinColumn(name = "inventory_id", referencedColumnName = "inventory_id",nullable = false)
    private Inventory inventory;

    public Long getInventoryStockHoldId() {
        return inventoryStockHoldId;
    }

    public void setInventoryStockHoldId(Long inventoryStockHoldId) {
        this.inventoryStockHoldId = inventoryStockHoldId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantityHold() {
        return quantityHold;
    }

    public void setQuantityHold(Integer quantityHold) {
        this.quantityHold = quantityHold;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
