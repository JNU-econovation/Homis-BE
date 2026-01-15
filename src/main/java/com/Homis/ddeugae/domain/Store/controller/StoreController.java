package com.Homis.ddeugae.domain.Store.controller;

import com.Homis.ddeugae.common.dto.ApiResponse;
import com.Homis.ddeugae.domain.Store.service.StoreService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    // 내 스토어 접속
    @GetMapping("/my-page")
    public ResponseEntity<ApiResponse<?>> loadMyPage(HttpServletRequest request){
        Long userDataId = (Long) request.getAttribute("userDataId");

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success("200", "내 스토어 미리보기 리스트 반환 성공",
                        storeService.loadMyPagePreview(userDataId)));
    }
}
