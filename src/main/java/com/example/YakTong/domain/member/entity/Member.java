package com.example.YakTong.domain.member.entity;

import com.example.YakTong.domain.auth.user.entity.UserEntity;
import com.example.YakTong.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // 일반 사용자 PK

    /* 역방향 (FK 없음) */
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "member")
    @Setter(AccessLevel.PACKAGE) // UserEntity 편의메서드에서만 세팅
    private UserEntity user;

    

}
