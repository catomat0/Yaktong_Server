package com.example.YakTong.domain.auth.user.dto.request.signUp;

import com.example.YakTong.domain.auth.user.entity.RoleType;
import com.example.YakTong.domain.auth.user.entity.UserEntity;
import com.example.YakTong.domain.pharmacy.entity.Pharmacy;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Schema(description = "약국 사용자 회원가입 요청 DTO")
public record PharmacySignUpRequest(

        // fields

        @NotBlank(message = "아이디는 필수 입력값입니다.") @Size(min = 4, max = 30, message = "아이디는 최소 4자 이상, 최대 30자 이하입니다.")
        @Schema(description = "로그인 ID", example = "login1234")
        String loginId,

        @NotBlank(message = "비밀번호는 필수 입력값입니다.")
        @Size(min = 8, max = 20, message = "비밀번호는 최소 8자 이상, 최대 20자 이하입니다.")
        @Schema(description = "비밀번호", example = "password1234")
        String password,

        @Schema(description = "사용자 이름(표시명)", example = "토마토")
        @NotBlank @Size(min = 1, max = 50)
        String username,

        @Email(message = "올바른 이메일 형식이어야 합니다.")
        @Schema(description = "사용자 이메일", example = "yaktong@example.com")
        String email,

        // 약국 필드

        @NotBlank(message = "약국 고유코드(사업자번호)는 필수 입력값입니다.")
        @Schema(description = "약국 사용자 가입용 - 약국 고유코드 (사업자번호)", example = "1234567890")
        String pharmacyCode,

        @NotBlank(message = "약국명은 필수 입력값입니다.")
        @Size(min = 2, max = 100, message = "약국명은 최소 2자 이상, 최대 100자 이하로 입력해야 합니다.")
        @Schema(description = "약국명 (일반 사용자 → 이름 기반 검색용 Kakao + 공공데이터 이름)", example = "서울중앙약국")
        String pharmacyName,

        @NotBlank(message = "전화번호는 필수 입력값입니다.")
        @Schema(description = "약국 전화번호", example = "02-1234-5678")
        String phone,

        @NotBlank(message = "행정구역 코드는 필수 입력값입니다.")
        @Schema(description = "행정구역 코드 (보건소가 담당하는 구역 - 시/군/구)", example = "11010")
        String regionCode,

        @NotBlank(message = "행정구역 이름은 필수 입력값입니다.")
        @Size(min = 2, max = 50, message = "행정구역 이름은 최소 2자 이상, 최대 50자 이하로 입력해야 합니다.")
        @Schema(description = "행정구역 이름 (세부 구역 - 동)", example = "강남구 역삼동")
        String regionName,

        @Schema(description = "약국 개설일자", example = "2020-05-01T09:00:00")
        LocalDateTime openDate


) {

    public Pharmacy toPharmacy() {

        return Pharmacy.builder()
                .pharmacyCode(pharmacyCode)
                .pharmacyName(pharmacyName)
                .phone(phone)
                .regionCode(regionCode)
                .regionName(regionName)
                .openDate(openDate)
                .build();
    }

    public UserEntity toUser(Pharmacy pharmacy, String encodedPassword) {

        return UserEntity.builder()
                .loginId(loginId)
                .password(encodedPassword)
                .username(username)
                .email(email)
                .role(RoleType.ROLE_PHARMACY)
                .isLock(false)
                .isSocial(false)
                .pharmacy(pharmacy) // member 1:1 매핑


                .build();
    }
}
