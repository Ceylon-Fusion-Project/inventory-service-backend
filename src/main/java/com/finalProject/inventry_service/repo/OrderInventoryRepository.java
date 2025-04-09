package com.finalProject.inventry_service.repo;

import com.finalProject.inventry_service.model.OrderInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderInventoryRepository extends JpaRepository<OrderInventory, Long> {
}
