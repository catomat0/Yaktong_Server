package com.example.YakTong.domain.prescription.repository;

import com.example.YakTong.domain.prescription.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionRepository extends JpaRepository<Prescription,Long> {
}
