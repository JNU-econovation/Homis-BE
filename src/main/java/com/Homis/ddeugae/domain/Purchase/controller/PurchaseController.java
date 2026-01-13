package com.Homis.ddeugae.domain.Purchase.controller;

import com.Homis.ddeugae.common.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/purchase")
@RequiredArgsConstructor
public class PurchaseController {
    // 구매 저장
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<?>> purchasePostAndSave(
            HttpServletRequest request, @RequestBody Long slaePostId){

        // TODO : 구매 내역 저장, sale에 purchased count 올리기

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success("200", "도안 구매 성공!"));
    }
}
