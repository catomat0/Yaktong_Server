package com.example.YakTong.domain.auth.user.controller;

import com.example.YakTong.domain.auth.user.dto.request.UserRequest;
import com.example.YakTong.domain.auth.user.dto.request.signUp.HealthCenterSignUpRequest;
import com.example.YakTong.domain.auth.user.dto.request.signUp.MemberSignUpRequest;
import com.example.YakTong.domain.auth.user.dto.request.signUp.PharmacySignUpRequest;
import com.example.YakTong.domain.auth.user.dto.response.UserResponse;
import com.example.YakTong.domain.auth.user.dto.response.signUp.SignUpResponse;
import com.example.YakTong.domain.auth.user.service.UserService;
import com.example.YakTong.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
@Tag(name = "User API", description = "회원 가입 / 유저 조회")
public class UserController {

    private final UserService userService;


    /**
     * 멤버(일반 사용자) 회원가입
     */
    @PostMapping("/signup/member")
    @Operation(
            summary = "일반 사용자 회원가입",
            description = "신규 일반 사용자를 등록합니다."
    )
    public ApiResponse<SignUpResponse> signupMemberApi(@Valid @RequestBody MemberSignUpRequest request) {

        log.info("일반 회원 가입 요청 - loginId: {}", request.loginId());
        SignUpResponse response = userService.signUpMember(request);
        return ApiResponse.success(response, "회원가입 성공");
    }

    /**
     * 약국 사용자 회원가입
     */
    @PostMapping("/signup/pharmacy")
    @Operation(
            summary = "약국 사용자 회원가입",
            description = "신규 약국 사용자를 등록합니다."
    )
    public ApiResponse<SignUpResponse> signupPharmacyApi(@Valid @RequestBody PharmacySignUpRequest request) {

        log.info("약국 회원 가입 요청 - loginId: {}", request.loginId());
        SignUpResponse response = userService.signUpPharmacy(request);
        return ApiResponse.success(response, "회원가입 성공");
    }

    /**
     * 보건소 사용자 회원가입
     */
    @PostMapping("/signup/healthcenter")
    @Operation(
            summary = "보건소 사용자 회원가입",
            description = "신규 보건소 사용자를 등록합니다."
    )
    public ApiResponse<SignUpResponse> signupHealthCenterApi(@Valid @RequestBody HealthCenterSignUpRequest request) {

        log.info("보건소 회원 가입 요청 - loginId: {}", request.loginId());
        SignUpResponse response = userService.signUpHealthCenter(request);
        return ApiResponse.success(response, "회원가입 성공");
    }

    /**
     * 전체 유저 정보
     */
    @GetMapping("/search/users")
    @Operation(
            summary = "전체 유저 조회",
            description = "시스템에 등록된 모든 사용자 정보를 조회합니다."
    )
    public ApiResponse<List<UserResponse>> getUserAllApi() {
        return ApiResponse.success(userService.getAllUsers(), "가입 유저 조회");
    }

}
