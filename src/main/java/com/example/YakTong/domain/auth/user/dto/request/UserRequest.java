package com.example.YakTong.domain.auth.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "사용자 요청 DTO")
public record UserRequest(

        @NotBlank(message = "아이디는 필수 입력값입니다.")
        @Size(min = 4, max = 30, message = "아이디는 최소 4자 이상, 최대 30자 이하입니다.")
        @Schema(description = "사용자 아이디", example = "user1234")
        String loginId,

        @NotBlank(message = "비밀번호는 필수 입력값입니다.")
        @Size(min = 8, max = 20, message = "비밀번호는 최소 8자 이상, 최대 20자 이하입니다.")
        @Schema(description = "비밀번호", example = "password1234")
        String password,

        @NotBlank(message = "유저명은 필수 입력값입니다.")
        @Size(min = 2, max = 20, message = "닉네임은 최소 2자 이상, 최대 20자 이하입니다.")
        @Schema(description = "사용자 이름", example = "토마토")
        String username,

        @Email(message = "올바른 이메일 형식이어야 합니다.")
        @Schema(description = "사용자 이메일", example = "yaktong@example.com")
        String email,

        @NotBlank(message = "비밀번호는 필수 입력값입니다.")
        @Schema(description = "사용자 타입", example = "ROLE_PHARMACY")
        String roleType

) {
}
