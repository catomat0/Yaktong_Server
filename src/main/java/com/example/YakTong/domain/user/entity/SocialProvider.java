package com.example.YakTong.domain.user.entity;

public enum SocialProvider {

    NAVER("네이버"),
    GOOGLE("구글");

    private final String description;

    SocialProvider(String description) {
        this.description = description;
    }
}
