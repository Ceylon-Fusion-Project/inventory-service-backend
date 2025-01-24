package com.finalProject.inventry_service.repo;

import com.finalProject.inventry_service.model.ReleaseInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReleaseInventoryRepository extends JpaRepository<ReleaseInventory, Long> {
}
