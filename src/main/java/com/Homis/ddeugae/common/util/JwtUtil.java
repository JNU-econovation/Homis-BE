package com.Homis.ddeugae.common.util;

import com.Homis.ddeugae.dto.JwtTokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    private final SecretKey secretKey;

    public JwtUtil(@Value("${jwt.secretKey}") String homisJwtKey) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(homisJwtKey));
    }

    public String createAccessToken(Long userDataId, String nickname){
        final String accessToken = Jwts.builder()
                .claim("userDataId", userDataId)
                .claim("userNickname", nickname)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 7200000)) // 3시간 유효 7200000
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();

        return accessToken;
    }

    public String createRefreshToken(Long userDataId, String nickname){
        String refreshToken = Jwts.builder()
                .claim("userDataId", userDataId)
                .claim("userNickname", nickname)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 90 * 1000L)) // 3개월 유효
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();

        return refreshToken;
    }

    public JwtTokenDto createLoginResponse(Long userDataId, String nickname){
        final String access = createAccessToken(userDataId, nickname);
        final String refresh = createRefreshToken(userDataId, nickname);

        return new JwtTokenDto(access, refresh, nickname);
    }

    // JWT 까주는 함수
    public Claims extractToken(String token) {
        Claims claims = Jwts.parser().verifyWith(this.secretKey).build()
                .parseSignedClaims(token).getPayload();
        return claims; // JWT에 집어넣은 정보 반환
    }

}
