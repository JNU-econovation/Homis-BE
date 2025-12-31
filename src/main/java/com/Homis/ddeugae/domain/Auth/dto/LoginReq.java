package com.Homis.ddeugae.domain.Auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginReq {
    @NotBlank(message = "ID 입력값이 없습니다.")
    private String userName;
    @NotBlank(message = "비밀번호 입력값이 없습니다.")
    private String userPassword;
}