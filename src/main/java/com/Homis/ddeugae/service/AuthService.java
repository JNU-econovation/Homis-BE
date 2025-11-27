package com.Homis.ddeugae.service;

import com.Homis.ddeugae.common.enumType.ErrorCode;
import com.Homis.ddeugae.common.exception.CustomException;
import com.Homis.ddeugae.common.util.Pbkdf2Encoder;
import com.Homis.ddeugae.dto.JwtTokenDto;
import com.Homis.ddeugae.dto.LoginDto;
import com.Homis.ddeugae.dto.SignupDto;
import com.Homis.ddeugae.entity.User;
import com.Homis.ddeugae.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final Pbkdf2Encoder pwdEncoder;
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

    public JwtTokenDto userLogin(LoginDto loginDto){
        // 아이디 일치 확인
        // 비밀번호 일치 확인 (pwdEncoder 사용)
        // jwt 토큰 발급
        return ;// 토큰 dto 반환
    }
}
