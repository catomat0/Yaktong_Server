package com.example.YakTong.domain.healthCenter.entity;

import com.example.YakTong.domain.pharmacy.entity.Pharmacy;
import com.example.YakTong.domain.user.entity.UserEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class HealthCenter {

    @Id
    @GeneratedValue
    private Long id;            // 보건소 고유 ID

    private String name;        // 보건소 이름
    private String region;      // 행정구역 (동일 행정구역의 약국과 매핑)
    private String address;     // 보건소 주소
    private String phone;       // 보건소 대표 전화번호

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;          // 보건소 계정 (ROLE_HEALTH_CENTER)

    @OneToMany(mappedBy = "healthCenter")
    private List<Pharmacy> pharmacies = new ArrayList<>();  // 보건소 소속 약국들
}