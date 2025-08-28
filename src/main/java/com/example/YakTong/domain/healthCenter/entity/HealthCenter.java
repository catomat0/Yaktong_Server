package com.example.YakTong.domain.healthCenter.entity;

import com.example.YakTong.domain.pharmacy.entity.Pharmacy;
import com.example.YakTong.domain.auth.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "health_center")
public class HealthCenter {

    @Id
    @GeneratedValue
    private Long id;

    private String centerName;  // 보건소 이름
    private String regionCode;  // 행정구역 코드 (동일 행정구역의 약국과 매핑)
    private String address;     // 보건소 주소
    private String phone;       // 보건소 대표 전화번호


    /* 역방향 (FK 없음) - User 1:1 */
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "healthCenter")
    @Setter(AccessLevel.PACKAGE)
    private UserEntity user;

    /* 보건소 소속 약국들 */
    @OneToMany(mappedBy = "healthCenter", orphanRemoval = false)
    @Builder.Default
    private List<Pharmacy> pharmacies = new ArrayList<>();

}