package com.example.YakTong.domain.medicine.repository;

import com.example.YakTong.domain.medicine.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {
}
