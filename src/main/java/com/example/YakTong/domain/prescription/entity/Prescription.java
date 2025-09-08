package com.example.YakTong.domain.prescription.entity;

import com.example.YakTong.domain.medicine.entity.Medicine;
import com.example.YakTong.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "prescription")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 처방전 이미지 S3
    @Column(length = 500)
    private String imageKey;

    /** 비고/메모 */
    @Column(length = 500)
    private String note;


    // ============= 매핑 필드 =============

    // 멤버는 여러 처방전 보유
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    @Setter
    private Member member;

    // 처방 약품 - 처방전 다대다 매핑
    @ManyToMany
    @JoinTable(
            name = "prescription_medicine",
            joinColumns = @JoinColumn(name = "prescription_id"),
            inverseJoinColumns = @JoinColumn(name = "medicine_id")
    )
    @Builder.Default
    private List<Medicine> medicines = new ArrayList<>();

}
