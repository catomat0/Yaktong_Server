package com.example.YakTong.domain.pharmacy.inventory.repository;

import com.example.YakTong.domain.pharmacy.inventory.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {
}
