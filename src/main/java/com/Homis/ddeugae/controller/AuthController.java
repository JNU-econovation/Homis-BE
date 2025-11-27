package com.Homis.ddeugae.controller;

import com.Homis.ddeugae.dto.ApiResponse;
import com.Homis.ddeugae.dto.JwtTokenDto;
import com.Homis.ddeugae.dto.LoginDto;
import com.Homis.ddeugae.dto.SignupDto;
import com.Homis.ddeugae.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ApiResponse<?>> logIn(@RequestBody @Valid LoginDto loginRequest) {

        JwtTokenDto loginData = authService.userLogin(loginRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success("200", "로그인 성공", loginData));
    }
}
