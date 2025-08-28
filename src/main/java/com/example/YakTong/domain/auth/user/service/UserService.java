package com.example.YakTong.domain.auth.user.service;

import com.example.YakTong.domain.auth.user.dto.request.signUp.HealthCenterSignUpRequest;
import com.example.YakTong.domain.auth.user.dto.request.signUp.MemberSignUpRequest;
import com.example.YakTong.domain.auth.user.dto.request.signUp.PharmacySignUpRequest;
import com.example.YakTong.domain.auth.user.dto.response.UserResponse;
import com.example.YakTong.domain.auth.user.dto.response.signUp.SignUpResponse;
import com.example.YakTong.domain.auth.user.entity.UserEntity;
import com.example.YakTong.domain.auth.user.repository.UserRepository;
import com.example.YakTong.domain.healthCenter.entity.HealthCenter;
import com.example.YakTong.domain.member.entity.Member;
import com.example.YakTong.domain.pharmacy.entity.Pharmacy;
import com.example.YakTong.global.error.code.GlobalErrorCode;
import com.example.YakTong.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    /**
     *  Cascade -> persist
     *  Member / Pharmacy / HealthCenter
     *  모두 UserEntity 가 가지므로 user 저장 시
     *  자동 매핑 + 자동 저장
     * private final MemberRepository memberRepository;
     * private final PharmacyRepository pharmacyRepository;
     * private final HealthCenterRepository healthCenterRepository;
     */


    ///  Service의 구조 receive => RequestDTO -> Entity -> Save -> ResponseDTO => return

    /**
     * 회원 가입 - 일반 사용자
     */
    @Transactional
    public SignUpResponse signUpMember(MemberSignUpRequest request) {

        // 아이디 중복 검증
        validateDuplicateLoginId(request.loginId());

        // Member 생성
        Member member = request.toMember();

        // User 생성 + Member 관계 매핑
        String encodedPassword = passwordEncoder.encode(request.password());
        UserEntity user = request.toUser(member, encodedPassword);

        // 저장
        UserEntity savedUser = userRepository.save(user);

        // Response 리턴
        return SignUpResponse.of(savedUser);
    }

    /**
     * 회원 가입 - 약국 사용자
     */
    @Transactional
    public SignUpResponse signUpPharmacy(PharmacySignUpRequest request) {

        // 아이디 중복 검증
        validateDuplicateLoginId(request.loginId());

        // Member 생성
        Pharmacy pharmacy = request.toPharmacy();

        // User 생성 + Member 관계 매핑
        String encodedPassword = passwordEncoder.encode(request.password());
        UserEntity user = request.toUser(pharmacy, encodedPassword);

        // 저장
        UserEntity savedUser = userRepository.save(user);

        // Response 리턴
        return SignUpResponse.of(savedUser);
    }

    /**
     * 회원 가입 - 보건소 사용자
     */
    @Transactional
    public SignUpResponse signUpHealthCenter(HealthCenterSignUpRequest request) {

        // 아이디 중복 검증
        validateDuplicateLoginId(request.loginId());

        // Member 생성
        HealthCenter healthCenter = request.toHealthCenter();

        // User 생성 + Member 관계 매핑
        String encodedPassword = passwordEncoder.encode(request.password());
        UserEntity user = request.toUser(healthCenter, encodedPassword);

        // 저장
        UserEntity savedUser = userRepository.save(user);

        // Response 리턴
        return SignUpResponse.of(savedUser);
    }


    /**
     * 로그인 ID 중복 체크
     */
    private void validateDuplicateLoginId(String loginId) {
        if (isLoginIdDuplicated(loginId)) {
            throw new CustomException(GlobalErrorCode.BAD_REQUEST, "이미 존재하는 로그인 아이디입니다.");
        }
    }

    public boolean isLoginIdDuplicated(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {

        List<UserEntity> users = userRepository.findAll();

        return users.stream()
                .map(UserResponse::from)
                .toList();
    }
}
