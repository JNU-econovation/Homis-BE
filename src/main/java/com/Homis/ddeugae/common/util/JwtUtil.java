package com.Homis.ddeugae.common.util;

import com.Homis.ddeugae.dto.JwtTokenDto;
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
    @Value("${jwt.secretKey}")
    private String homisJwtKey;

    private final SecretKey secretKey =
            Keys.hmacShaKeyFor(Decoders.BASE64.decode(homisJwtKey));

    public String createAccessToken(String nickName){
        final String accessToken = Jwts.builder()
                .claim("userNickname", nickName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 7200000)) // 3시간 유효
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        return accessToken;
    }

    public String createRefreshToken(String nickName){
        String refreshToken = Jwts.builder()
                .claim("userNickname", nickName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 90 * 1000L)) // 3개월 유효
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        return refreshToken;
    }

    public JwtTokenDto createLoginResponse(String nickName){
        final String access = createAccessToken(nickName);
        final String refresh = createRefreshToken(nickName);

        return new JwtTokenDto(access, refresh, nickName);
    }

}
