package com.example.YakTong.domain.auth.user.dto.request.signUp;

import com.example.YakTong.domain.auth.user.entity.RoleType;
import com.example.YakTong.domain.auth.user.entity.UserEntity;
import com.example.YakTong.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "멤버 사용자 회원가입 요청 DTO")
public record MemberSignUpRequest(

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
        String email

) {
    public Member toMember() {

        return Member.builder()
                .build();
    }

    public UserEntity toUser(Member member, String encodedPassword) {

        return UserEntity.builder()
                .loginId(loginId)
                .password(encodedPassword)
                .username(username)
                .email(email)
                .role(RoleType.ROLE_MEMBER)
                .isLock(false)
                .isSocial(false)
                .member(member) // member 1:1 매핑
                .build();
    }

}