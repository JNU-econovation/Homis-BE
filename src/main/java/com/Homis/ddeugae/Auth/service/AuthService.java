package com.Homis.ddeugae.Auth.service;

import com.Homis.ddeugae.common.exception.ErrorCode;
import com.Homis.ddeugae.common.exception.CustomException;
import com.Homis.ddeugae.Auth.jwt.JwtProvider;
import com.Homis.ddeugae.common.util.Pbkdf2Encoder;
import com.Homis.ddeugae.Auth.dto.JwtTokenDto;
import com.Homis.ddeugae.Auth.dto.LoginReq;
import com.Homis.ddeugae.Auth.dto.SignupDto;
import com.Homis.ddeugae.Auth.entity.User;
import com.Homis.ddeugae.Auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final Pbkdf2Encoder pwdEncoder;
    private final JwtProvider jwtProvider;

    public void registerUser(SignupDto signupDto){
        if (userRepository.findByUserName(signupDto.getUserName()).isPresent()){
            throw new CustomException(ErrorCode.DUPLICATED_USER_NAME);
        }
        if (userRepository.findByUserNickname(signupDto.getUserNickname()).isPresent()){
            throw new CustomException(ErrorCode.DUPLICATED_USER_NICKNAME);
        }

        if (signupDto.getUserName().equals(signupDto.getUserPassword())){
            throw new CustomException(ErrorCode.SAME_NAME_PASSWORD);
        }

        User user = User.builder()
                .userName(signupDto.getUserName())
                .userPassword(pwdEncoder.encode(signupDto.getUserPassword()))
                .userNickname(signupDto.getUserNickname())
                .build();

        userRepository.save(user);
    }

    public JwtTokenDto userLogin(LoginReq loginDto){
        final String userName = loginDto.getUserName();
        final String userPassword = loginDto.getUserPassword();

        // 존재하는 사용자인지 확인
        if (userRepository.findByUserName(userName).isEmpty()){
            throw new CustomException(ErrorCode.NOT_USER);
        }
        final User userDoc = userRepository.findByUserName(userName).get();

        // 비밀번호 일치 확인 (pwdEncoder 사용)
        final String hashedPwd = userDoc.getUserPassword();
        if (!pwdEncoder.matches(userPassword, hashedPwd)){
            throw new CustomException(ErrorCode.WRONG_PWD);
        }

        // 데이터 Id, 닉네임 조회
        final Long userDataId = userDoc.getUserDataId();
        final String userNickname = userDoc.getUserNickname();

        // 로그인 response 데이터 생성
        final JwtTokenDto jwtToken = jwtProvider.createLoginResponse(userDataId, userNickname);

        // refresh 토큰 user 테이블에 저장

        return jwtToken;
    }
}
