package com.Homis.ddeugae.Auth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class JwtTokenDto {
    private final String accessToken;
    private final String refreshToken;
    private final String userNickname;

    public JwtTokenDto(String access, String refresh, String nickname){
        if (access == null || refresh == null || nickname == null){
            throw new IllegalArgumentException("Jwt 토큰 발급에 null값 발생");
        }

        this.accessToken = access;
        this.refreshToken = refresh;
        this.userNickname = nickname;
    }
}
