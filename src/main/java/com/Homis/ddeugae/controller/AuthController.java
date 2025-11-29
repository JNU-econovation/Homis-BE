package com.Homis.ddeugae.controller;

import com.Homis.ddeugae.dto.*;
import com.Homis.ddeugae.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<?>> signUp(@RequestBody @Valid SignupDto signupRequest ) {
        authService.registerUser(signupRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("201", "회원가입 성공"));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> logIn(@RequestBody @Valid LoginReqDto loginRequest) {

        JwtTokenDto jwtToken = authService.userLogin(loginRequest);

        LoginRespDto loginData = new LoginRespDto(jwtToken.getAccessToken(), jwtToken.getUserNickname());

        ResponseCookie cookie = ResponseCookie.from("refreshToken", jwtToken.getRefreshToken())
                .httpOnly(true).secure(true).sameSite("None")
                .path("/api/auth/refresh")
                .maxAge(60 * 60 * 24 * 90)  // 리프레시 토큰은 3개월 유효
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(ApiResponse.success("200", "로그인 성공", loginData));
    }

    // TODO - /refresh 리프레시 토큰을 통한 액세스 토큰 재발급

    // TODO - /logout 로그아웃 api 명세 작성, 구현 => refreshToken 쿠키 삭제 + DB 레코드 삭제
}
