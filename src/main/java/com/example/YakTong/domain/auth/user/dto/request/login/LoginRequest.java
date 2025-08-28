package com.example.YakTong.domain.auth.user.dto.request.login;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 멤버, 약국, 보건소 -> 아이디 + 비밀번호 = 로그인
 */
@Schema(description = "로그인 요청 DTO")
public record LoginRequest (

        @Schema(description = "로그인 ID", example = "login1234")
        @NotBlank(message = "아이디는 필수 입력값입니다.") @Size(min = 4, max = 30, message = "아이디는 최소 4자 이상, 최대 30자 이하입니다.")
        String loginId,

        @NotBlank(message = "비밀번호는 필수 입력값입니다.")
        @Size(min = 8, max = 20, message = "비밀번호는 최소 8자 이상, 최대 20자 이하입니다.")
        @Schema(description = "비밀번호", example = "password1234")
        String password

){

}
