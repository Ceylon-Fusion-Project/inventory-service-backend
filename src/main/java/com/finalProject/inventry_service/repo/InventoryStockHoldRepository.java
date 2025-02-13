package com.finalProject.inventry_service.repo;

import com.finalProject.inventry_service.model.InventoryStockHold;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryStockHoldRepository extends JpaRepository<InventoryStockHold, Long> {

}
