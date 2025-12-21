package com.Homis.ddeugae.Auth.controller;

import com.Homis.ddeugae.Auth.dto.JwtTokenDto;
import com.Homis.ddeugae.Auth.dto.LoginReq;
import com.Homis.ddeugae.Auth.dto.LoginResp;
import com.Homis.ddeugae.Auth.dto.SignupDto;
import com.Homis.ddeugae.common.dto.ApiResponse;
import com.Homis.ddeugae.Auth.service.AuthService;
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
    public ResponseEntity<ApiResponse<?>> logIn(@RequestBody @Valid LoginReq loginRequest) {

        JwtTokenDto jwtToken = authService.userLogin(loginRequest);

        LoginResp loginData = new LoginResp(jwtToken.getAccessToken(), jwtToken.getUserNickname());

        ResponseCookie cookie = ResponseCookie.from("refreshToken", jwtToken.getRefreshToken())
                .httpOnly(true).secure(false).sameSite("None") // TODO - 배포 시 true 변경
                .path("/api/auth/refresh")
                .maxAge(60 * 60 * 24 * 90)  // 리프레시 토큰은 3개월 유효
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(ApiResponse.success("200", "로그인 성공", loginData));
    }

    // TODO - /refresh 리프레시 토큰을 통한 액세스 토큰 재발급

    // 로그아웃 API
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<?>> logOut(){
        ResponseCookie cookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true).secure(false).sameSite("None") // TODO - 배포 시 true 변경
                .path("/api/auth/refresh")
                .maxAge(0)  // 리프레시 토큰은 3개월 유효
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(ApiResponse.success("200", "로그아웃 성공"));
    }
}
