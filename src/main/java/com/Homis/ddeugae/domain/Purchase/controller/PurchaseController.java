package com.Homis.ddeugae.domain.Purchase.controller;

import com.Homis.ddeugae.common.dto.ApiResponse;
import com.Homis.ddeugae.domain.Purchase.dto.PurchaseSaveReq;
import com.Homis.ddeugae.domain.Purchase.repository.PurchasePreviewMapping;
import com.Homis.ddeugae.domain.Purchase.service.PurchaseService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase")
@RequiredArgsConstructor
public class PurchaseController {
    final PurchaseService purchaseService;

    // 구매 저장
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<?>> purchasePostAndSave(
            HttpServletRequest request, @RequestBody @Valid PurchaseSaveReq saveReq){

        Long userDataId = (Long) request.getAttribute("userDataId");

        purchaseService.purchaseSalePost(userDataId, saveReq.getSalePostId());

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success("200", "도안 구매 성공!"));
    }

    // 구매 미리보기 리스트 반환
    @GetMapping("/preview")
    public ResponseEntity<ApiResponse<?>> purchasePreview(HttpServletRequest request){
        Long userDataId = (Long) request.getAttribute("userDataId");

        List<PurchasePreviewMapping> previewData = purchaseService.getPurchasedPreview(userDataId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success("200", "도안 구매 미리보기 리스트 반환 성공", previewData));
    }
}
