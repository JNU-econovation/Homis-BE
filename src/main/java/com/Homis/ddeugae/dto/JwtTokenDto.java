package com.Homis.ddeugae.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtTokenDto {
    private final String accessToken;

    private final String refreshToken;

    private final String userNickname;
}
