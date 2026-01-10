package com.Homis.ddeugae.domain.Make.controller;

import com.Homis.ddeugae.common.exception.CustomException;
import com.Homis.ddeugae.common.enumType.ErrorCode;
import com.Homis.ddeugae.common.util.BlobStorageManager;
import com.Homis.ddeugae.domain.Make.dto.MadeFileDownloadInfoDto;
import com.Homis.ddeugae.domain.Make.dto.MadeFileDownloadReq;
import com.Homis.ddeugae.domain.Make.dto.MadeUploadReq;
import com.Homis.ddeugae.common.dto.ApiResponse;
import com.Homis.ddeugae.domain.Make.repository.MadeDetailMapping;
import com.Homis.ddeugae.domain.Make.repository.MadePreviewMapping;
import com.Homis.ddeugae.domain.Make.service.MakeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/design-make")
@RequiredArgsConstructor
public class MakeController {
    private static final Logger log = LoggerFactory.getLogger(MakeController.class);
    private final MakeService makeService;
    private final BlobStorageManager blobStorageManager;

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
    public ResponseEntity<ApiResponse<?>> madePreview(HttpServletRequest request) {
        Long userDataId = (Long) request.getAttribute("userDataId");

        List<MadePreviewMapping> MadePreviewData = makeService.getMadePreview(userDataId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success("200",
                        "도안 제작 미리보기 내용 불러오기 성공 (메인 페이지 구성용)",
                        MadePreviewData));
    }

    @GetMapping("/detail")
    public ResponseEntity<ApiResponse<?>> loadMadeDetail(
            HttpServletRequest request,
            @RequestParam(name = "madeDataId") Long madeDataId) {

        Long userDataId = (Long) request.getAttribute("userDataId");

        MadeDetailMapping madeDetailData = makeService.getMadeDetail(userDataId, madeDataId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success("200", "도안 제작 상세페이지 내용 불러오기 성공",
                        madeDetailData));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<?>> deleteMadePost(
            HttpServletRequest request,
            @RequestParam(name = "madeDataId") Long madeDataID) {
        Long userDataId = (Long) request.getAttribute("userDataId");

        makeService.deleteMadePost(userDataId, madeDataID);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success("200", "도안 제작 게시글 삭제 성공"));
    }

    @PostMapping("/download")
    public ResponseEntity<StreamingResponseBody> downloadImgFile(
            HttpServletRequest request,
            @RequestBody MadeFileDownloadReq downloadReq){
        Long userDataId = (Long) request.getAttribute("userDataId");

        MadeFileDownloadInfoDto info = makeService.getDownloadInfo(
                                            userDataId, downloadReq.getMadeDataId(), downloadReq.getFileType());

        String encodedFileName =
                URLEncoder.encode(downloadReq.getMadeName(), StandardCharsets.UTF_8).replace("+", "%20");

        String downloadFileName = "Knit_Doa-" + encodedFileName + "-" + UUID.randomUUID() + info.getExtension();

        StreamingResponseBody responseBody = outputStream ->  {
            try (InputStream is = blobStorageManager.downloadBlobToStream(info.getBlobUrl())) {
                is.transferTo(outputStream);

            } catch (IOException ie) {
                log.error("[파일 다운로드 실패] blobUrl={}", info.getBlobUrl(), ie);
                throw new CustomException(ErrorCode.BLOB_FAILED_LOAD_STREAM, ie);
            }
        };

        return ResponseEntity.ok()
                .header("Content-Disposition",
                        "attachment; filename*=UTF-8''" + downloadFileName)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(responseBody);
    }
}