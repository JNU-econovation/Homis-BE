package com.Homis.ddeugae.domain.Sale.controller;

import com.Homis.ddeugae.common.dto.ApiResponse;
import com.Homis.ddeugae.domain.Sale.dto.SaleUploadReq;
import com.Homis.ddeugae.domain.Sale.service.SaleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/sale")
@RequiredArgsConstructor
public class SaleController {
    private final SaleService saleService;
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<?>> salePostUpload(
            HttpServletRequest request,
            @RequestParam(name = "saleImgFiles") MultipartFile[] saleImgs,
            @RequestParam(name = "salePdfFile") MultipartFile salePdf,
            @RequestBody @Valid SaleUploadReq uploadReqBody ){
        Long userDataId = (Long) request.getAttribute("userDataId");

        saleService.uploadSalePost(userDataId, saleImgs, salePdf, uploadReqBody);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("201", "도안 상품 등록 성공!"));
    }
}
