package com.example.YakTong.domain.auth.user.entity;

import com.example.YakTong.domain.auth.oauth2.SocialProvider;
import com.example.YakTong.domain.healthCenter.entity.HealthCenter;
import com.example.YakTong.domain.member.entity.Member;
import com.example.YakTong.domain.pharmacy.entity.Pharmacy;
import com.example.YakTong.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "user_entity")
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login_id", unique = true, nullable = false, updatable = false)
    private String loginId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "is_lock", nullable = false)
    private Boolean isLock;

    @Column(name = "is_social", nullable = false)
    private Boolean isSocial;

    @Enumerated(EnumType.STRING)
    @Column(name = "social_provider")
    private SocialProvider socialProvider;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private RoleType role;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;


    // 1:1 (FK: user_entity.member_id / pharmacy_id / health_center_id)
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "member_id", unique = true)
    private Member member;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pharmacy_id", unique = true)
    private Pharmacy pharmacy;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "health_center_id", unique = true)
    private HealthCenter healthCenter;

    public void updateEmail(@Email(message = "올바른 이메일 형식이어야 합니다.") String email) {

        this.email = email;
    }
}