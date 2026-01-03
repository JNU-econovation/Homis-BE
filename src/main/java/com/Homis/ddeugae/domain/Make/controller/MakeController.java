package com.Homis.ddeugae.domain.Make.controller;

import com.Homis.ddeugae.domain.Make.dto.MadeUploadReq;
import com.Homis.ddeugae.common.dto.ApiResponse;
import com.Homis.ddeugae.domain.Make.dto.MadeUploadResp;
import com.Homis.ddeugae.domain.Make.service.MakeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
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
    private final MakeService makeService;

    // 도안 제작 내용 저장 API
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<?>> madeUpload(
            HttpServletRequest request,
            @RequestBody @Valid MadeUploadReq uploadReq) {

        Long userDataId = (Long) request.getAttribute("userDataId");

        makeService.uploadMadeDesign(uploadReq, userDataId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("201", "도안 제작 내용 업로드(저장) 성공"));
    }
}
