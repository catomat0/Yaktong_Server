package com.example.YakTong.domain.user.entity;

import com.example.YakTong.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.*;

@Entity
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String loginId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    private String name;
    private String phone;
    private String region;

    @Builder
    public User(String loginId, Role role, String password, String email, String name, String phone, String region) {
        this.loginId = loginId;
        this.role = role;
        this.password = password;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.region = region;
    }

}