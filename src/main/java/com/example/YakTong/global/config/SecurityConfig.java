package com.example.YakTong.global.config;

import com.example.YakTong.domain.auth.filter.JWTFilter;
import com.example.YakTong.domain.auth.filter.LoginFilter;
import com.example.YakTong.domain.auth.handler.RefreshTokenLogoutHandler;
import com.example.YakTong.domain.auth.jwt.service.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static com.example.YakTong.domain.auth.user.entity.RoleType.*;

@Configuration
@EnableWebSecurity
//@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    private final JwtService jwtService;

    private final AuthenticationSuccessHandler loginSuccessHandler;
    private final AuthenticationSuccessHandler socialSuccessHandler;


    public SecurityConfig(
            AuthenticationConfiguration authenticationConfiguration,
            JwtService jwtService,
            @Qualifier("LoginSuccessHandler") AuthenticationSuccessHandler loginSuccessHandler,
            @Qualifier("SocialSuccessHandler") AuthenticationSuccessHandler socialSuccessHandler
    ) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtService = jwtService;
        this.loginSuccessHandler = loginSuccessHandler;
        this.socialSuccessHandler = socialSuccessHandler;
    }

    // 커스텀 자체 로그인 필터를 위한 AuthenticationManager Bean 수동 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    // 비밀번호 단방향(BCrypt) 암호화 메서드
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // CORS Bean
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(List.of("Authorization", "Set-Cookie"));
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // 권한 계층
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.withRolePrefix("ROLE_")
                .role(ROLE_ADMIN.name()).implies(ROLE_MEMBER.name())
                .role(ROLE_ADMIN.name()).implies(ROLE_PHARMACY.name())
                .role(ROLE_ADMIN.name()).implies(ROLE_HEALTHCENTER.name())
                .build();
    }


    // SecurityFilterChain Bean
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // CSRF 보안 필터 disable
        http
                .csrf(AbstractHttpConfigurer::disable);

        // CORS 설정
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));

        // 기본 Form 기반 인증 필터들 disable
        http
                .formLogin(AbstractHttpConfigurer::disable);

        // 기본 Basic 인증 필터 disable
        http
                .httpBasic(AbstractHttpConfigurer::disable);

        // 기본 로그아웃 필터 + 커스텀 Refresh 토큰 삭제 핸들러 추가
        http
                .logout(logout -> logout
                        .addLogoutHandler(new RefreshTokenLogoutHandler(jwtService)));

        // OAuth2 인증용
        http
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(socialSuccessHandler));

        // 외부에서 swagger 목록 조회 가능
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll());

        // 인가
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/jwt/exchange", "/jwt/refresh").permitAll() // 토큰 인증
                        .requestMatchers("/admin/**").hasAuthority(ROLE_ADMIN.name()) // 운영 전용
                        .requestMatchers(HttpMethod.POST, "/api/v1/user/**").permitAll() // 자체 회원 가입
                        .anyRequest().authenticated()
                );

        // S3 이미지 업로드 인가
        http.authorizeHttpRequests(auth -> auth
                // 업로드: 로그인 + 특정 롤만 허용
                .requestMatchers(org.springframework.http.HttpMethod.POST, "/image/upload")
                .hasAnyAuthority(
                        ROLE_MEMBER.name(),
                        ROLE_PHARMACY.name(),
                        ROLE_HEALTHCENTER.name()
                        // RoleType.ROLE_ADMIN.name()  // 관리자 롤을 쓰면 추가 (혹은 계층 연결 시 자동 허용)
                )
                .anyRequest().authenticated()
        );

        // 예외 처리
        http
                .exceptionHandling(e -> e
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED); // 401 응답
                        })
                        .accessDeniedHandler((request, response, authException) -> {
                            response.sendError(HttpServletResponse.SC_FORBIDDEN); // 403 응답
                        })
                );

        // 커스텀 필터 추가
        http
                .addFilterBefore(new LoginFilter(authenticationManager(authenticationConfiguration), loginSuccessHandler), UsernamePasswordAuthenticationFilter.class);

        // JWT 필터
        http
                .addFilterBefore(new JWTFilter(), LogoutFilter.class);

        // 세션 필터 설정 (STATELESS)
        http
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
