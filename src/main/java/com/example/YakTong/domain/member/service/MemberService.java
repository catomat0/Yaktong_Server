package com.example.YakTong.domain.member.service;

import com.example.YakTong.domain.auth.jwt.service.JwtService;
import com.example.YakTong.domain.auth.oauth2.CustomOAuth2User;
import com.example.YakTong.domain.auth.oauth2.SocialProvider;
import com.example.YakTong.domain.member.repository.MemberRepository;
import com.example.YakTong.domain.auth.user.dto.request.UserRequest;
import com.example.YakTong.domain.auth.user.dto.response.UserResponse;
import com.example.YakTong.domain.auth.user.entity.UserEntity;
import com.example.YakTong.domain.auth.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.YakTong.domain.auth.user.entity.RoleType.ROLE_MEMBER;

@Service
@RequiredArgsConstructor
public class MemberService extends DefaultOAuth2UserService implements UserDetailsService {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    // 자체 로그인 (Spring Security에서 사용) -> Member 로 이동하기
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        UserEntity entity = userRepository
                .findByLoginIdAndIsLockAndIsSocial(loginId, false, false) // ← 변경
                .orElseThrow(() -> new UsernameNotFoundException(loginId));

        return User.builder()
                .username(entity.getLoginId()) // Authentication.getName() == loginId
                .password(entity.getPassword())
                .roles(entity.getRole().name().replace("ROLE_", ""))
                .accountLocked(entity.getIsLock())
                .build();
    }


    // 소셜 로그인 (신규 가입 or 기존 업데이트)
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> attributes;
        List<GrantedAuthority> authorities;
        String loginId;
        String role = ROLE_MEMBER.name();
        String email;
        String username;

        String registrationId = userRequest.getClientRegistration().getRegistrationId().toUpperCase();

        if (registrationId.equals(SocialProvider.NAVER.name())) { // Naver
            attributes = (Map<String, Object>) oAuth2User.getAttributes().get("response");
            loginId = registrationId + "_" + attributes.get("id");
            email = attributes.get("email").toString();
            username = attributes.get("name").toString();

        } else if (registrationId.equals(SocialProvider.GOOGLE.name())) { // Google
            attributes = (Map<String, Object>) oAuth2User.getAttributes();
            loginId = registrationId + "_" + attributes.get("sub");
            email = attributes.get("email").toString();
            username = attributes.get("name").toString();

        } else {
            throw new OAuth2AuthenticationException("지원하지 않는 소셜 로그인입니다.");
        }

        Optional<UserEntity> entity = userRepository.findByLoginIdAndIsSocial(loginId, true);
        if (entity.isPresent()) {
            role = entity.get().getRole().name();
//            entity.get().update();
        } else {
            UserEntity newUserEntity = UserEntity.builder()
                    .loginId(loginId)
                    .password("") // 소셜은 패스워드 없음
                    .isLock(false)
                    .isSocial(true)
                    .socialProvider(SocialProvider.valueOf(registrationId))
                    .role(ROLE_MEMBER)
                    .username(username)
                    .email(email)
                    .build();
            userRepository.save(newUserEntity);
        }

        authorities = List.of(new SimpleGrantedAuthority(role));
        return new CustomOAuth2User(attributes, authorities, loginId);
    }

    // 회원 정보 수정 -> Member 로 이동하기
    @Transactional
    public UserResponse updateUser(UserRequest dto) throws AccessDeniedException {

        UserEntity entity = userRepository
                .findByLoginIdAndIsLockAndIsSocial(dto.loginId(), false, false) // 변경
                .orElseThrow(() -> new UsernameNotFoundException(dto.loginId()));

        entity.updateEmail(dto.email()); // username = 표시명

        return UserResponse.from(entity);
    }

    // 회원 탈퇴 -> Member 로 이동하기
    @Transactional
    public void deleteUser(UserRequest dto) throws AccessDeniedException {

        SecurityContext context = SecurityContextHolder.getContext();
        String sessionUsername = context.getAuthentication().getName();
        String sessionRole = context.getAuthentication().getAuthorities().iterator().next().getAuthority();

        boolean isOwner = sessionUsername.equals(dto.loginId());
        boolean isAdmin = "ROLE_ADMIN".equals(sessionRole);

        if (!isOwner && !isAdmin) {
            throw new AccessDeniedException("본인 혹은 관리자만 삭제할 수 있습니다.");
        }

        userRepository.deleteByLoginId(dto.loginId()); // loginId
        jwtService.removeRefreshUser(dto.loginId());

    }
}
