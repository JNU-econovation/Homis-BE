package com.Homis.ddeugae.domain.Auth.dto;

import lombok.Getter;

@Getter
public class LoginResp {
    private final String accessToken;
    private final String userNickname;

    public LoginResp(String access, String nickname){
        if(access == null || nickname == null){
            throw new IllegalArgumentException("로그인 request body data 중 null이 있습니다.");
        }

        this.accessToken = access;
        this.userNickname = nickname;
    }
}
