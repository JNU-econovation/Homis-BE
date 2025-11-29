package com.Homis.ddeugae.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRespDto {
    private final String tokenType = "Bearer";

    private final String accessToken;

    private final String userNickname;
}
