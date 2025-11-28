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
        String accessToken = Jwts.builder()
                .claim("userNickname", nickName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 7200000))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return accessToken;
    }

    public String createRefreshToken(){
        // accesstoken처럼 만들면ㄷ 되는 걸까??
    }

    public JwtTokenDto createJwtToken(String nickName){
        // JWT dto 사용해서 return
    }
}
