package com.Homis.ddeugae.domain.Auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class SignupReq {

    @NotBlank(message = "ID 값이 없습니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "ID는 영문과 숫자만 가능합니다.")
    @Length(min=4, max=12, message = "ID는 최소 4자, 최대 12자여야 합니다.")
    private String userName;

    @NotBlank(message = "비밀번호 값이 없습니다.")
    @Length(min=8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String userPassword;

    @NotBlank(message = "닉네임 값이 없습니다.")
    @Length(max=10, message = "닉네임은 최대 10자입니다.")
    private String userNickname;
}
