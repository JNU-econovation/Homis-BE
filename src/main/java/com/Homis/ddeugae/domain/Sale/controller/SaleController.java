package com.Homis.ddeugae.domain.Sale.controller;

import com.Homis.ddeugae.common.dto.ApiResponse;
import com.Homis.ddeugae.common.util.BlobStorageManager;
import com.Homis.ddeugae.domain.Sale.dto.SaleUploadReq;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/sale")
@RequiredArgsConstructor
public class SaleController {
    private final BlobStorageManager blobStorageManager;
    
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<?>> salePostUpload(
            HttpServletRequest request,
            @RequestParam(name = "saleImgFiles") MultipartFile[] saleImgs,
            @RequestParam(name = "salePdfFile") MultipartFile salePdf,
            @RequestBody @Valid SaleUploadReq uploadReqBody ){
        Long userDataId = (Long) request.getAttribute("userDataId");

        List<String> saleImgUrls = blobStorageManager.uploadFilesFromStream(saleImgs);
        String salePdfUrl = blobStorageManager.uploadFileFromStream(salePdf);

        // 파일 업로드 url + 요청 body db에 저장

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("201", "도안 상품 등록 성공!"));
    }
}
