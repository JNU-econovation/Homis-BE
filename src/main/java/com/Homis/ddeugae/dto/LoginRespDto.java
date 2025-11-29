package com.Homis.ddeugae.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginRespDto {
    private final String tokenType = "Bearer";

    @NotNull(message = "액세스 토큰값이 없습니다.")
    private final String accessToken;

    @NotNull(message = "사용자 닉네임 값이 없습니다.")
    private final String userNickname;
}
