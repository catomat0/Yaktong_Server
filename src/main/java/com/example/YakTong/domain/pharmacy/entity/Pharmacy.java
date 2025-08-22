package com.example.YakTong.domain.pharmacy.entity;

import com.example.YakTong.domain.healthCenter.entity.HealthCenter;
import com.example.YakTong.domain.user.entity.UserEntity;
import com.example.YakTong.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter @NoArgsConstructor
public class Pharmacy extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                   // 내부 PK

    private String pharmacyCode;       // 약국 사용자 가입용 - 공공데이터 약국 고유코드 (사업자번호/기관코드)
    private String pharmacyName;       // 약국명 (일반 사용자 → 이름 기반 검색용)

    // 보건소 사용자용 필드 + 약국 위치 (카카오맵 api 캐싱용) -> from 마커
    private String regionCode;         // 행정구역 코드 (시/군/구)
    private String regionName;         // 행정구역 이름
    private LocalDateTime openDate;    // 개설일자
    private String status;             // 영업 상태 (영업중, 휴업, 폐업 등)
    private String address;            // 지번 주소
    private String roadAddress;        // 도로명 주소
    private Double latitude;           // 위도
    private Double longitude;          // 경도

    // 약국 사용자 계정 (ROLE_PHARMACY) 1:1
    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private UserEntity user;

    // 소속 행정구역의 보건소 매핑
    @ManyToOne
    @JoinColumn(name = "health_center_id")
    private HealthCenter healthCenter;   // 소속 보건소

}
