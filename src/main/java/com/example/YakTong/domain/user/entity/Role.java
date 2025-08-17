package com.example.YakTong.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter @RequiredArgsConstructor
public enum Role {

    PATIENT ("ROLE_PATIENT", "환자"),
    PHARMACY ("ROLE_PHARMACY", "약국"),
    HEALTHCENTER ("ROLE_HEALTHCENTER", "보건소")
    ;

    private final String authority;
    private final String description;

    @Override
    public String toString() {
        return description;
    }

}
