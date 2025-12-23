package com.Homis.ddeugae.domain.Make.controller;

import com.Homis.ddeugae.domain.Make.dto.MadeUploadDto;
import com.Homis.ddeugae.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/design-make")
@RequiredArgsConstructor
public class MakeController {

    // 도안 제작 내용 저장 API
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<?>> madeUpload(@RequestBody @Valid MadeUploadDto uploadReq) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("201", "제작 도안 저장(업로드) 성공"));
    }
}
