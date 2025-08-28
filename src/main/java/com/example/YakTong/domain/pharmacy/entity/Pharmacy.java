package com.example.YakTong.domain.pharmacy.entity;

import com.example.YakTong.domain.healthCenter.entity.HealthCenter;
import com.example.YakTong.domain.auth.user.entity.UserEntity;
import com.example.YakTong.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "pharmacy")
public class Pharmacy extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                   // 내부 PK

    private String pharmacyCode;       // 약국 사용자 가입용 - 약국 고유코드 (사업자번호)
    private String pharmacyName;       // 약국명 (일반 사용자 → 이름 기반 검색용 Kakao + 공공데이터 이름)
    private String phone;              // 약국 전화번호

    // 약국 <-> 보건소
    private String regionCode;         // 행정구역 코드 (보건소가 담당하는 구역 - 시/군/구)
    private String regionName;         // 행정구역 이름 (세부 구역 - 동)
    private LocalDateTime openDate;    // 개설일자

    // 약국 위치 (카카오맵 api 캐싱용) -> from 마커
    private String address;            // 지번 주소
    private String roadAddress;        // 도로명 주소
    private String latitude;           // 위도
    private String longitude;          // 경도

    /* 역방향 (FK 없음) - User 1:1 */
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "pharmacy")
    @Setter(AccessLevel.PACKAGE)
    private UserEntity user;

    /* 보건소 매핑 (N:1) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "health_center_id")
    private HealthCenter healthCenter;

}
