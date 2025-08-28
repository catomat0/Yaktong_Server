package com.example.YakTong.domain.auth.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter @RequiredArgsConstructor
public enum RoleType {
    ROLE_ADMIN("ADMIN", "시스템 관리자"),
    ROLE_MEMBER("MEMBER", "일반"),
    ROLE_PHARMACY ("PHARMACY", "약국 관리자"),
    ROLE_HEALTHCENTER ("HEALTHCENTER", "보건소")
    ;

    private final String authority;
    private final String description;

    @Override
    public String toString() {
        return description;
    }

}
