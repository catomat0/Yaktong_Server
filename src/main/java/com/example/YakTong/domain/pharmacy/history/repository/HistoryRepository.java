package com.example.YakTong.domain.pharmacy.history.repository;

import com.example.YakTong.domain.pharmacy.history.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History,Long> {
}
