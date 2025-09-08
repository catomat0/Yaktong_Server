package com.example.YakTong.domain.pharmacy.inventory.entity;

import com.example.YakTong.domain.medicine.entity.Medicine;
import com.example.YakTong.domain.pharmacy.entity.Pharmacy;
import com.example.YakTong.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "inventory")
public class Inventory extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 현재 재고 수량
    @Column(nullable = false)
    @Builder.Default
    private Integer quantity = 0;

    // 사용자 노출 여부 스위치 (true = 일반 사용자에게 보임 - 일반) / (false = 일반 사용자에게 가림 - 마약류)
    @Column(name = "is_visible", nullable = false)
    @Builder.Default
    private Boolean isVisible = true;

    // 특이 사항 기입 필드
    @Column(length = 500)
    @Builder.Default
    private String note = "-";

    // 매핑 필드

    // 재고 보유 약국
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pharmacy_id", nullable = false)
    @Setter
    private Pharmacy pharmacy;

    // Medicine 매핑
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine;

}

