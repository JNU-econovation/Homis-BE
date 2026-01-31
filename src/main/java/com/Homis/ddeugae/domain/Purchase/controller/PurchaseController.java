package com.Homis.ddeugae.domain.Purchase.controller;

import com.Homis.ddeugae.common.dto.ApiResponse;
import com.Homis.ddeugae.common.enumType.ErrorCode;
import com.Homis.ddeugae.common.exception.CustomException;
import com.Homis.ddeugae.common.util.BlobStorageManager;
import com.Homis.ddeugae.domain.Purchase.dto.PurchaseDownloadInfoDto;
import com.Homis.ddeugae.domain.Purchase.dto.PurchaseDownloadReq;
import com.Homis.ddeugae.domain.Purchase.dto.PurchaseSaveReq;
import com.Homis.ddeugae.domain.Purchase.repository.PurchasePreviewMapping;
import com.Homis.ddeugae.domain.Purchase.service.PurchaseService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/purchase")
@RequiredArgsConstructor
public class PurchaseController {
    final PurchaseService purchaseService;
    final BlobStorageManager blobStorageManager;

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

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<?>> purchasePostDelete(
            HttpServletRequest request, @RequestParam(name = "purchasedPostId") Long postId){
        Long userDataId = (Long) request.getAttribute("userDataId");

        purchaseService.deletePurchasePost(userDataId, postId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success("200", "구매 게시글 삭제 성공"));
    }

    // 도안 구매 게시글 -> pdf 다운로드
    @PostMapping("/download")
    public ResponseEntity<StreamingResponseBody> purchasedPdfDownload(
            HttpServletRequest request, @RequestBody @Valid PurchaseDownloadReq downloadReq){
        Long userDataId = (Long) request.getAttribute("userDataId");

        // 정보 생성 (파일명, pdf url)
        PurchaseDownloadInfoDto downloadInfo =
                purchaseService.getDownloadInfo(userDataId, downloadReq.getPurchasedPostId());

        // body 생성
        StreamingResponseBody responseBody = outputStream -> {
            try (InputStream is = blobStorageManager.downloadBlobToStream(downloadInfo.getDownloadPdfUrl())) {
                is.transferTo(outputStream);

            } catch (IOException ie) {
                throw new CustomException(ErrorCode.BLOB_FAILED_LOAD_STREAM, ie);
            }
        };

        return ResponseEntity.ok()
                .header("Content-Disposition",
                        "attachment; filename*=UTF-8''" + downloadInfo.getDownloadFilename())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(responseBody);
    }
}