package com.finalProject.inventry_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "order_inventory")
public class OrderInventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_inventory_id", nullable = false)
    private Long orderInventoryId;
    @Column(name = "product_id", nullable = false)
    private Long productId;
    @Column(name = "order_quantity", nullable = false)
    private Integer OrderQuentity;
    @Column(name = "order_id", nullable = false)
    private Long orderId;


    public Long getOrderInventoryId() {
        return orderInventoryId;
    }

    public void setOrderInventoryId(Long orderInventoryId) {
        this.orderInventoryId = orderInventoryId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getOrderQuentity() {
        return OrderQuentity;
    }

    public void setOrderQuentity(Integer orderQuentity) {
        OrderQuentity = orderQuentity;
    }
}
