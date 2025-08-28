package com.example.YakTong.domain.auth.user.dto.response.login;

import com.example.YakTong.domain.auth.user.entity.RoleType;
import com.example.YakTong.domain.auth.user.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "로그인 응답 DTO(유저 요약, 토큰 제외)")
@Builder
public record LoginResponse(
        @Schema(description = "User PK") Long userId,
        @Schema(description = "로그인 ID") String loginId,
        @Schema(description = "표시명") String username,
        @Schema(description = "이메일") String email,
        @Schema(description = "역할") RoleType role,
        @Schema(description = "Member PK") Long memberId,
        @Schema(description = "Pharmacy PK") Long pharmacyId,
        @Schema(description = "HealthCenter PK") Long healthCenterId
) {
    public static LoginResponse of(UserEntity u) {
        return new LoginResponse(
                u.getId(),
                u.getLoginId(),
                u.getUsername(),
                u.getEmail(),
                u.getRole(),
                u.getMember() != null ? u.getMember().getId() : null,
                u.getPharmacy() != null ? u.getPharmacy().getId() : null,
                u.getHealthCenter() != null ? u.getHealthCenter().getId() : null
        );
    }
}
