package com.Homis.ddeugae.common.exception;

import com.Homis.ddeugae.common.enumType.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;
}