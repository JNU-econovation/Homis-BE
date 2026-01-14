package com.Homis.ddeugae.domain.Main.controller;

import com.Homis.ddeugae.common.dto.ApiResponse;
import com.Homis.ddeugae.domain.Main.dto.MainLoadResp;
import com.Homis.ddeugae.domain.Make.repository.MadePreviewMapping;
import com.Homis.ddeugae.domain.Make.service.MakeService;
import com.Homis.ddeugae.domain.Purchase.repository.PurchasePreviewMapping;
import com.Homis.ddeugae.domain.Purchase.service.PurchaseService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/main")
@RequiredArgsConstructor
public class MainController {
    private final MakeService makeService;
    private final PurchaseService purchaseService;

    @GetMapping("/load")
    public ResponseEntity<ApiResponse<?>> loadMainPage(HttpServletRequest request){
        Long userDataId = (Long) request.getAttribute("userDataId");

        List<MadePreviewMapping> madePreviewData = makeService.getMadePreview(userDataId);
        List<PurchasePreviewMapping> purchasePreviewData = purchaseService.getPurchasedPreview(userDataId);

        MainLoadResp mainLoadRespData = MainLoadResp.builder()
                .madePreview(madePreviewData).purchasePreview(purchasePreviewData).build();

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success("200", "메인페이지 로드 완료", mainLoadRespData));
    }
}
