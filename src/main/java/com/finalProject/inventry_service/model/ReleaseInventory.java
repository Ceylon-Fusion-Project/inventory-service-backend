package com.finalProject.inventry_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "release_inventory")
public class ReleaseInventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_release_id", nullable = false)
    private Long inventoryReleaseId;
    @Column(name = "product_id", nullable = false)
    private Long productId;
    @Column(name = "quantity_released", nullable = false)
    private Integer quantityReleased;
    @ManyToOne
    @JoinColumn(name = "inventory_id", referencedColumnName = "inventory_id",nullable = false)
    private Inventory inventory;

    public Long getInventoryReleaseId() {
        return inventoryReleaseId;
    }

    public void setInventoryReleaseId(Long inventoryReleaseId) {
        this.inventoryReleaseId = inventoryReleaseId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantityReleased() {
        return quantityReleased;
    }

    public void setQuantityReleased(Integer quantityReleased) {
        this.quantityReleased = quantityReleased;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
