package com.Homis.ddeugae.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private final boolean success;   // 성공 여부
    private final String code;       // HTTP 상태 코드
    private final String message;    // 메시지
    private final T data;            // 반환 데이터 (null 가능)

    // -- 성공
    // data 없음
    public static <T> ApiResponse<T> success(String code, String message){
        return new ApiResponse<T>(true, code, message, null);
    }
    // data 있음
    public static <T> ApiResponse<T> success(String code, String message, T data){
        return new ApiResponse<T>(true, code, message, data);
    }

    // -- 에러(실패) -> data 없음
    public static <T> ApiResponse<T> error(String code, String message){
        return new ApiResponse<T>(false, code, message, null);
    }
}
