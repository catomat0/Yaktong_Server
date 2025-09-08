package com.example.YakTong.domain.pharmacy.history.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HistoryType {

    IN("IN","입고"),
    OUT("OUT","출고"),
    RETURN("RETURN","반품"),
    EXPIRE("EXPIRE","폐기"),
    ADJUST("ADJUST","정정")
    ;

    private final String code;
    private final String description;

    @Override
    public String toString() {
        return description;
    }
}
