package com.example.YakTong.domain.pharmacy.repository;

import com.example.YakTong.domain.pharmacy.entity.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacyRepository extends JpaRepository<Pharmacy,Long> {
}
