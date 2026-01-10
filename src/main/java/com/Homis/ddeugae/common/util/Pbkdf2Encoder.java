package com.Homis.ddeugae.common.util;

import com.Homis.ddeugae.common.enumType.ErrorCode;
import com.Homis.ddeugae.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value; // Lombok으로 하면 안됨!!!
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

@Component // Bean 등록
@RequiredArgsConstructor
public class Pbkdf2Encoder {
    @Value("${password.encode.algorithm}")
    private String encodeAlgorithm;

    @Value("${password.encode.iterations}")
    private int iterations;

    @Value("${password.encode.keyLength}")
    private int keyLength;

    private static final int SALT_LENGTH = 16;


    // --- 메소드
    public String encode(String rawPassword) { // "salt:hash" string 반환
        byte[] salt = generateSalt();
        String hash = generateHash(rawPassword, salt);
        return Base64.getEncoder().encodeToString(salt) + ":" + hash;
    }

    public boolean matches(String rawPassword, String encodedPassword) { // 비밀번호 일치 확인
        String[] parts = encodedPassword.split(":");                        // 앞은 salt(->parts[0]), 뒤는 hash(->parts[1])
        if (parts.length != 2) throw new CustomException(ErrorCode.SALT_HASH_PROB);  // 요소가 2개가 아니면 이상한 값이 들어온 것.

        byte[] salt = Base64.getDecoder().decode(parts[0]); // Base64 인코딩 상태인 salt 디코딩
        String hash = generateHash(rawPassword, salt);      // 들어온 raw비번 해싱 진행

        return hash.equals(parts[1]);   // 저장된(해싱된) 비밀번호와 일치한지 return
    }

    private String generateHash(String rawPassword, byte[] salt) { // 해싱 함수
        try {
            KeySpec spec = new PBEKeySpec(rawPassword.toCharArray(), salt, iterations, keyLength);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(encodeAlgorithm);
            byte[] hashBytes = factory.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.FAILED_HASHING_PWD);
        }
    }

    private byte[] generateSalt() {         // 정해진 길이만큼 salt 랜덤 생성
        byte[] salt = new byte[SALT_LENGTH];
        new SecureRandom().nextBytes(salt);
        return salt;
    }
}
