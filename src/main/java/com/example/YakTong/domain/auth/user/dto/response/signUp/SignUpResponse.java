package com.example.YakTong.domain.auth.user.dto.response.signUp;

import com.example.YakTong.domain.auth.user.entity.RoleType;
import com.example.YakTong.domain.auth.user.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "회원가입 응답 DTO(공통)")
@Builder
public record SignUpResponse(
        Long userId,
        String loginId,
        String username,
        String email,
        RoleType role,
        Long memberId,
        Long pharmacyId,
        Long healthCenterId
) {
    public static SignUpResponse of(UserEntity u) {
        return SignUpResponse.builder()
                .loginId(u.getLoginId())
                .username(u.getUsername())
                .email(u.getEmail())
                .role(u.getRole())
                .memberId(u.getMember() != null ? u.getMember().getId() : null)
                .pharmacyId(u.getPharmacy() != null ? u.getPharmacy().getId() : null)
                .healthCenterId(u.getHealthCenter() != null ? u.getHealthCenter().getId() : null)
                .build();
    }
}