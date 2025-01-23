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
    @Column(name = "inventory_release_id", nullable = false)
    private Long inventoryReleaseId;
    @Column(name = "product_id", nullable = false)
    private Long productId;
    @Column(name = "quantity_released", nullable = false)
    private Integer quantityReleased;
    @OneToOne
    @JoinColumn(name = "inventory_id", referencedColumnName = "inventory_id")
    private Inventory inventory;
}
