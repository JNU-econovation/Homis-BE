package com.Homis.ddeugae.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Getter
public class LoginRespDto {
    private final String accessToken;
    private final String userNickname;

    public LoginRespDto(String access, String nickname){
        if(access == null || nickname == null){
            throw new IllegalArgumentException("로그인 request body data 중 null이 있습니다.");
        }

        this.accessToken = access;
        this.userNickname = nickname;
    }
}
