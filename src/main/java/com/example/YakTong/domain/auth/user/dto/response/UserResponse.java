package com.example.YakTong.domain.auth.user.dto.response;

import com.example.YakTong.domain.auth.user.entity.RoleType;
import com.example.YakTong.domain.auth.user.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "사용자 응답 DTO")
@Builder
public record UserResponse(

        @Schema(description = "User 아이디", example = "1")
        Long userId,

        @Schema(description = "Member 아이디", example = "1")
        Long memberId,

        @Schema(description = "Pharmacy 아이디", example = "1")
        Long pharmacyId,

        @Schema(description = "HealthCenter 아이디", example = "1")
        Long healthCenterId,


        @Schema(description = "로그인 ID (회원가입 시 사용)", example = "login1234")
        String loginId,

        @Schema(description = "사용자 이름", example = "토마토")
        String username,

        @Schema(description = "이메일", example = "user1@naver.com")
        String email,

        @Schema(description = "회원 권한", example = "ROLE_MEMBER")
        RoleType role

) {
//    public static UserResponse of(Long userId, Long memberId, String loginId,
//                                  String username, RoleType role
//    ) {
//        return new UserResponse(userId, memberId, loginId, username, role);
//    }

    public static UserResponse from(UserEntity user) {
        return UserResponse.builder()
                .userId(user.getId())
                .memberId(user.getMember() != null ? user.getMember().getId() : null)
                .pharmacyId(user.getPharmacy() != null ? user.getPharmacy().getId() : null)
                .healthCenterId(user.getHealthCenter() != null ? user.getHealthCenter().getId() : null)
                .loginId(user.getLoginId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

}
