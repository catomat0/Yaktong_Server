package com.example.YakTong.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter @RequiredArgsConstructor
public enum RoleType {

    ROLE_PATIENT ("PATIENT", "환자"),
    ROLE_PHARMACY ("PHARMACY", "약국"),
    ROLE_HEALTHCENTER ("HEALTHCENTER", "보건소")
    ;

    private final String authority;
    private final String description;

    @Override
    public String toString() {
        return description;
    }

}
