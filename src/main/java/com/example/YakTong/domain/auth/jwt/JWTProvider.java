package com.example.YakTong.domain.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

//public class JWTUtil {
//
//    private static final SecretKey secretKey;
//    private static final Long accessTokenExpiresIn;
//    private static final Long refreshTokenExpiresIn;
//
//    static  {
//        String secretKeyString = "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@";
//        secretKey = new SecretKeySpec(secretKeyString.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
//
//        accessTokenExpiresIn = 3600L * 1000; // 1시간
//        refreshTokenExpiresIn = 604800L * 1000; // 7일
//    }
//
//
//    // JWT 클레임 username 파싱
//    public static String getUsername(String token) {
//        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("sub", String.class);
//    }
//
//    // JWT 클레임 role 파싱
//    public static String getRole(String token) {
//        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
//    }
//
//    // JWT 유효 여부 (위조, 시간, Access/Refresh 여부)
//    public static Boolean isValid(String token, Boolean isAccess) {
//        try {
//            Claims claims = Jwts.parser()
//                    .verifyWith(secretKey)
//                    .build()
//                    .parseSignedClaims(token)
//                    .getPayload();
//
//            String type = claims.get("type", String.class);
//            if (type == null) return false;
//
//            if (isAccess && !type.equals("access")) return false;
//            if (!isAccess && !type.equals("refresh")) return false;
//
//            return true;
//
//        } catch (JwtException | IllegalArgumentException e) {
//            return false;
//        }
//    }
//
//
//    // JWT(Access/Refresh) 생성
//    public static String createJWT(String username, String role, Boolean isAccess) {
//
//        long now = System.currentTimeMillis();
//        long expiry = isAccess ? accessTokenExpiresIn : refreshTokenExpiresIn;
//        String type = isAccess ? "access" : "refresh";
//
//        return Jwts.builder()
//                .claim("sub", username)
//                .claim("role", role)
//                .claim("type", type)
//                .issuedAt(new Date(now))
//                .expiration(new Date(now + expiry))
//                .signWith(secretKey)
//                .compact();
//    }
//}

@Component
public class JWTProvider {

    private static String secretKeyString;
    private static Long accessTokenExpiresIn;
    private static Long refreshTokenExpiresIn;

    private static SecretKey secretKey;

    public JWTProvider(
            @Value("${JWT_SECRET}") String secretKeyString,
            @Value("${JWT_ACCESS_MS}") Long accessTokenExpiresIn,
            @Value("${JWT_REFRESH_MS}") Long refreshTokenExpiresIn
    ) {
        this.secretKeyString = secretKeyString;
        this.accessTokenExpiresIn = accessTokenExpiresIn;
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
    }

    @PostConstruct
    private void init() {
        secretKey = new SecretKeySpec(
                secretKeyString.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm()
        );
    }

    // JWT 클레임 username 파싱
    public static String getUsername(String token) {
        return Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token)
                .getPayload()
                .get("sub", String.class);
    }

    // JWT 클레임 role 파싱
    public static String getRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);
    }

    // JWT 유효 여부 (위조, 시간, Access/Refresh 여부)
    public static Boolean isValid(String token, Boolean isAccess) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            String type = claims.get("type", String.class);
            if (type == null) return false;

            if (isAccess && !type.equals("access")) return false;
            if (!isAccess && !type.equals("refresh")) return false;

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // JWT(Access/Refresh) 생성
    public static String createJWT(String username, String role, Boolean isAccess) {
        long now = System.currentTimeMillis();
        long expiry = isAccess ? accessTokenExpiresIn : refreshTokenExpiresIn;
        String type = isAccess ? "access" : "refresh";

        return Jwts.builder()
                .claim("sub", username)
                .claim("role", role)
                .claim("type", type)
                .issuedAt(new Date(now))
                .expiration(new Date(now + expiry))
                .signWith(secretKey)
                .compact();
    }
}