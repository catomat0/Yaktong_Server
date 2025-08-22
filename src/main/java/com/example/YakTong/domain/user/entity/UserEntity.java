package com.example.YakTong.domain.user.entity;

import com.example.YakTong.domain.user.dto.UserRequestDTO;
import com.example.YakTong.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
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

    @Column(name = "username", unique = true, nullable = false, updatable = false)
    private String username;

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
    private UserRole role;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "email")
    private String email;


    public void updateUser(UserRequestDTO dto) {
        this.email = dto.getEmail();
        this.nickname = dto.getNickname();
    }
}