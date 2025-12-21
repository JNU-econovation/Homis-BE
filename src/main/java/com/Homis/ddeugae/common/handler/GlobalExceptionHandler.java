package com.Homis.ddeugae.common.handler;

import com.Homis.ddeugae.common.enumType.ErrorCode;
import com.Homis.ddeugae.common.exception.CustomException;
import com.Homis.ddeugae.common.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    // Custom Exception들 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<?>> customHandler(CustomException ce){
        final ErrorCode errorCode = ce.getErrorCode();
        final HttpStatus status = errorCode.getStatus();
        final String code = errorCode.getCode();
        final String message = errorCode.getMessage();

        return ResponseEntity.status(status)
                .body(ApiResponse.error(code, message));
    }

    // Valid 오류 처리 -> message 작성 내용 나오게 했음
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> validHandler(MethodArgumentNotValidException me) {
        String message = me.getBindingResult()
                .getAllErrors().getLast().getDefaultMessage();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("400", message));
    }

    // 생성자에서 null값 등 입력값 잘못된 오류 처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<?>> validHandler(IllegalArgumentException Ie) {
        String message = Ie.getMessage();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("500", message));
    }


}