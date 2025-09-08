package com.example.YakTong.domain.pharmacy.history.entity;

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
@Table(name = "history")
public class History extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 이력 타입 (입고, 출고, 정정, 폐기)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private HistoryType type;

    // 수량 변화 (+/-)
    @Column(nullable = false)
    private Integer qtyChange;

    // 변화 후 재고 - 최종값
    @Column(nullable = false)
    private Integer qtyAfter;

    // 변경 사유
    @Column(length = 500)
    private String reason;

    // 접근 제한 (true = 약국/보건소만 열람 가능)
    @Builder.Default
    @Column(name = "restricted", nullable = false)
    private Boolean restricted = true;


    // 매핑 필드

    // 소유자 - 약국
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pharmacy_id", nullable = false)
    private Pharmacy pharmacy;

    // 소유자 -  약품
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine;
}
