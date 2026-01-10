package com.Homis.ddeugae.common.exception;

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
    NOT_FOUND_USER(HttpStatus.UNAUTHORIZED, "105_NOT_FOUND_USER","존재하지 않는 사용자에 대한 토큰"),

    // ---403
    NOT_OWNER(HttpStatus.FORBIDDEN, "300_NOT_OWNER", "게시글 작성자가 아닙니다."),

    // ---404
    NOT_FOUND_MADE(HttpStatus.NOT_FOUND, "400_NOT_FOUND_MADE", "존재하지 않는 도안 제작 게시글에 대한 접근"),
    BLOB_NOT_FOUND(HttpStatus.NOT_FOUND, "401_BLOB_NOT_FOUND", "존재하지 않는 blob 삭제를 시도했음"),
    NOT_FOUND_IMG_FILE(HttpStatus.NOT_FOUND, "402_NOT_FOUND_IMG_FILE", "게시글의 이미지 url이 존재하지 않음"),

    // ---409
    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "901_DUPLICATED_USER_NAME", "이미 등록된 아이디"),
    DUPLICATED_USER_NICKNAME(HttpStatus.CONFLICT,"902_DUPLICATED_USER_NICKNAME", "이미 등록된 닉네임"),
    WRONG_PWD(HttpStatus.CONFLICT, "903_WRONG_PWD", "비밀번호가 틀렸습니다."),

    // ---500
    SALT_HASH_PROB(HttpStatus.INTERNAL_SERVER_ERROR, "001_SALT_HASH_PROB", "salt:hash 구조가 아님"),
    FAILED_HASHING_PWD(HttpStatus.INTERNAL_SERVER_ERROR, "002_FAILED_HASHING_PWD", "비밀번호 해싱 중 실패"),
    FAILED_LOAD_IMG(HttpStatus.INTERNAL_SERVER_ERROR, "003_FAILED_LOAD_IMG", "이미지 byte 배열 로드 실패"),
    FAILED_WEBSNAPSHOT_API(HttpStatus.INTERNAL_SERVER_ERROR, "004_FAILED_WEBSNAPSHOT_API", "렌더링 및 스크린샷 API 오류 발생"),
    BLOB_FAILED_UPLOAD_FILE(HttpStatus.INTERNAL_SERVER_ERROR, "005_BLOB_FAILED_UPLOAD_FILE", "Blob Storage에 파일 업로드 실패"),
    UNKNOWN_FAILED_UPLOAD_FILE(HttpStatus.INTERNAL_SERVER_ERROR, "006_UNKNOWN_FAILED_UPLOAD_FILE", "알 수 없는 파일 업로드 오류"),
    FAILED_LOAD_PDF(HttpStatus.INTERNAL_SERVER_ERROR, "007_FAILED_LOAD_PDF", "PDF byte 배열 로드 실패"),
    FAILED_ADD_IMG_TO_PDF(HttpStatus.INTERNAL_SERVER_ERROR, "008_FAILED_ADD_IMG_TO_PDF", "PDF에 도안 이미지 추가 중 오류 발생"),
    FAILED_LOAD_FONT(HttpStatus.INTERNAL_SERVER_ERROR, "009_FAILED_LOAD_FONT", "PDF용 폰트 파일 로드 실패"),
    FAILED_SAVE_PDF(HttpStatus.INTERNAL_SERVER_ERROR, "0010_FAILED_SAVE_PDF", "PDF 파일 저장 실패"),
    PDFBOX_FAILED_SETTING_STREAM(HttpStatus.INTERNAL_SERVER_ERROR, "0011_PDFBOX_FAILED_SETTING_STREAM", "PDF 파일 작성 stream 세팅 중 오류 발생"),
    PDFBOX_FAILED_NEW_PAGE(HttpStatus.INTERNAL_SERVER_ERROR, "0012_PDFBOX_FAILED_NEW_PAGE", "PDF 파일 내 새 페이지 추가 중 오류 발생"),
    PDFBOX_FAILED_NEW_LINE(HttpStatus.INTERNAL_SERVER_ERROR, "0013_PDFBOX_FAILED_NEW_LINE", "PDF 파일 내 텍스트 작성 중 줄바꿈 시 오류 발생"),
    PDFBOX_FAILED_WRITE_LINE(HttpStatus.INTERNAL_SERVER_ERROR, "0014_PDFBOX_FAILED_WRITE_LINE", "PDF 파일 내 텍스트 작성 실패"),
    PDFBOX_FAILED_WRAPPING(HttpStatus.INTERNAL_SERVER_ERROR, "0015_PDFBOX_FAILED_WRAPPING", "PDF 폭 너비 맞춰 텍스트 처리 중 오류"),
    PDFBOX_FAILED_CLOSE(HttpStatus.INTERNAL_SERVER_ERROR, "0016_PDFBOX_FAILED_CLOSE", "텍스트 전용 PDF 파일 작성 stream 닫기 실패"),
    BLOB_FAILED_DELETE(HttpStatus.INTERNAL_SERVER_ERROR, "0017_BLOB_FAILED_DELETE", "BLOB 삭제 중 오류 발생"),
    UNKNOWN_FAILED_DELETE_FILE(HttpStatus.INTERNAL_SERVER_ERROR, "0018_UNKNOWN_FAILED_DELETE_FILE", "알 수 없는 FILE 삭제 오류 발생"),
    BLOB_FAILED_LOAD_STREAM(HttpStatus.INTERNAL_SERVER_ERROR, "0019_BLOB_FAILED_LOAD_STREAM", "BLOB에 대해 STREAM 로드 실패");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
