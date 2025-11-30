package com.Homis.ddeugae.common.enumType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // ---400
    SAME_NAME_PASSWORD(HttpStatus.BAD_REQUEST, "001_SAME_NAME_PASSWORD", "ID와 비밀번호는 같을 수 없습니다."),
    NOT_USER(HttpStatus.BAD_REQUEST, "002_NOT_USER", "존재하지 않는 ID의 사용자입니다."),

    // ---401
    UNKNOWN_AUTH_ERROR(HttpStatus.UNAUTHORIZED, "100_UNKNOWN_AUTH_ERROR", "알 수 없는 사용자 인증 오류"),
    NOT_BEARER_TOKEN(HttpStatus.UNAUTHORIZED, "101_NOT_BEARER_TOKEN", "Bearer 토큰이 없습니다."),
    INVALID_ACCESS(HttpStatus.UNAUTHORIZED, "102_INVALID_ACCESS", "JWT 토큰 오류 발생"),
    EXPIRED_ACCESS(HttpStatus.UNAUTHORIZED, "103_EXPIRED_ACCESS", "액세스토큰이 만료되었습니다. 재발급 필요"),
    REQUIRED_RE_LOGIN(HttpStatus.UNAUTHORIZED, "104_REQUIRED_RE_LOGIN", "리프레시토큰까지 만료되었습니다. 재로그인 필요"),

    // ---409
    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "901_DUPLICATED_USER_NAME", "이미 등록된 아이디"),
    DUPLICATED_USER_NICKNAME(HttpStatus.CONFLICT,"902_DUPLICATED_USER_NICKNAME", "이미 등록된 닉네임"),
    WRONG_PWD(HttpStatus.CONFLICT, "903_WRONG_PWD", "비밀번호가 틀렸습니다."),

    // ---500
    SALT_HASH_PROB(HttpStatus.INTERNAL_SERVER_ERROR, "001_SALT_HASH_PROB", "salt:hash 구조가 아님"),
    FAILED_HASHING_PWD(HttpStatus.INTERNAL_SERVER_ERROR, "002_FAILED_HASHING_PWD", "비밀번호 해싱 중 실패");
    private final HttpStatus status;
    private final String code;
    private final String message;
}
