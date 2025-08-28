package com.example.YakTong.domain.medicine.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "medicine")
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;       // 의약품명
    private String category;   // 일반 / 전문
    private String description; // 종류 (진통제, 항생제, 소화제, ...)


    // prescription - 1 : n

}
