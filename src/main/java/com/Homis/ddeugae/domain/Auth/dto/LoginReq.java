package com.Homis.ddeugae.domain.Auth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginReq {
    @NotNull(message = "ID 입력값이 없습니다.")
    private String userName;
    @NotNull(message = "비밀번호 입력값이 없습니다.")
    private String userPassword;
}