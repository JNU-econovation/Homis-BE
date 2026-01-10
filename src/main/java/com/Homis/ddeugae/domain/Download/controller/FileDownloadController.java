package com.Homis.ddeugae.domain.Download.controller;

import com.Homis.ddeugae.common.dto.ApiResponse;
import com.Homis.ddeugae.domain.Download.dto.FileDownloadReq;
import com.Homis.ddeugae.domain.Make.service.MakeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/download")
@RequiredArgsConstructor
public class FileDownloadController {
    private final MakeService makeService;

    // [도안 제작] 이미지 파일 다운로드
    @PostMapping("/img")
    public ResponseEntity<ApiResponse<?>> downloadImgFile(
            HttpServletRequest request,
            @RequestBody FileDownloadReq downloadReq){
        Long userDataId = (Long) request.getAttribute("userDataId");
        Long madeDataId = downloadReq.getPostDataId();
        String blobUrl = downloadReq.getFileUrl();

        makeService.checkExistenceAndOwner(userDataId, madeDataId);

        // TODO : 이미지 파일 다운로드 스트림 가져와서 반환 (방식은 고민중,,,)
    }
}