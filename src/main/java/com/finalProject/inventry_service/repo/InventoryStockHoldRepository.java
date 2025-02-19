package com.finalProject.inventry_service.repo;

import com.finalProject.inventry_service.model.InventoryStockHold;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryStockHoldRepository extends JpaRepository<InventoryStockHold, Long> {

    Optional<InventoryStockHold> findByProductId(Long productId);

    InventoryStockHold findByOrderIdAndProductId(Long orderId, Long productId);

}
