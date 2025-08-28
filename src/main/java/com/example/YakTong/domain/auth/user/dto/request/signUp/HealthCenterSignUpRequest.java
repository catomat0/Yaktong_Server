package com.example.YakTong.domain.auth.user.dto.request.signUp;

import com.example.YakTong.domain.auth.user.entity.RoleType;
import com.example.YakTong.domain.auth.user.entity.UserEntity;
import com.example.YakTong.domain.healthCenter.entity.HealthCenter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "보건소 사용자 회원가입 요청 DTO")
public record HealthCenterSignUpRequest (

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

        @NotBlank(message = "보건소 이름은 필수 입력값입니다.")
        @Size(min = 2, max = 100, message = "보건소 이름은 최소 2자 이상, 최대 100자 이하로 입력해야 합니다.")
        @Schema(description = "보건소 이름", example = "강남구 보건소")
        String centerName,

        @NotBlank(message = "행정구역 코드는 필수 입력값입니다.")
        @Schema(description = "행정구역 코드 (법정동/행정동 기준)", example = "11680")
        String regionCode,

        @NotBlank(message = "보건소 주소는 필수 입력값입니다.")
        @Size(min = 5, max = 200, message = "보건소 주소는 최소 5자 이상, 최대 200자 이하로 입력해야 합니다.")
        @Schema(description = "보건소 주소", example = "서울특별시 강남구 선릉로 668")
        String address,

        @NotBlank(message = "전화번호는 필수 입력값입니다.")
        @Schema(description = "보건소 전화번호", example = "02-3423-5555")
        String phone


){


    public HealthCenter toHealthCenter() {

        return HealthCenter.builder()
                .centerName(centerName)
                .regionCode(regionCode)
                .address(address)
                .phone(phone)
                .build();
    }

    public UserEntity toUser(HealthCenter healthCenter, String encodedPassword) {

        return UserEntity.builder()
                .loginId(loginId)
                .password(encodedPassword)
                .username(username)
                .email(email)
                .role(RoleType.ROLE_HEALTHCENTER)
                .isLock(false)
                .isSocial(false)
                .healthCenter(healthCenter)
                .build();
    }
}
