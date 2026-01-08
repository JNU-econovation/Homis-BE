package com.Homis.ddeugae.domain.Make.controller;

import com.Homis.ddeugae.domain.Make.dto.MadeUploadReq;
import com.Homis.ddeugae.common.dto.ApiResponse;
import com.Homis.ddeugae.domain.Make.repository.MadePreviewMapping;
import com.Homis.ddeugae.domain.Make.service.MakeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.tags.Param;

import java.util.List;

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

    @GetMapping("/preview")
    public ResponseEntity<ApiResponse<?>> madePreview(HttpServletRequest request){
        Long userDataId = (Long) request.getAttribute("userDataId");

        List<MadePreviewMapping> MadePreviewData = makeService.getMadePreview(userDataId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success("200",
                                        "도안 제작 미리보기 내용 불러오기 성공 (메인 페이지 구성용)",
                                                MadePreviewData));
    }

    @GetMapping("/detail/{madeDataId}")
    public ResponseEntity<ApiResponse<?>> loadMadeDetail(
            HttpServletRequest request, Param queryParam){

    }
}
