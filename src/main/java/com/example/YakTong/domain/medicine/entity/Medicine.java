package com.example.YakTong.domain.medicine.entity;

import com.example.YakTong.domain.pharmacy.history.entity.History;
import com.example.YakTong.domain.pharmacy.inventory.entity.Inventory;
import com.example.YakTong.domain.prescription.entity.Prescription;
import com.example.YakTong.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "medicine")
public class Medicine extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;       // 의약품명

    private String code;       // 의약 코드

    // 전문의약품 여부 (true=전문의약품, false=일반의약품)
    @Builder.Default
    @Column(nullable = false)
    private Boolean prescriptionOnly = false;

    private String description; // 종류 (진통제, 항생제, 소화제, ...)

    // 약품 이미지 S3
    @Column(length = 500)
    private String imageUrl;

    // ================= 매핑 필드 =================

    // 처방전 - 다대다 (Prescription ↔ Medicine)
    @ManyToMany(mappedBy = "medicines")
    @Builder.Default
    private List<Prescription> prescriptions = new ArrayList<>();

    // 약국 재고 - 일대다 (한 약품이 여러 약국 재고에 포함될 수 있음)
    @OneToMany(mappedBy = "medicine", cascade = CascadeType.ALL, orphanRemoval = false)
    @Builder.Default
    private List<Inventory> inventories = new ArrayList<>();

    // 재고 이력 - 일대다 (약품별 입출고/폐기 이력)
    @OneToMany(mappedBy = "medicine", cascade = CascadeType.ALL, orphanRemoval = false)
    @Builder.Default
    private List<History> histories = new ArrayList<>();

}
