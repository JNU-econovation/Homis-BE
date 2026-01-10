package com.Homis.ddeugae.domain.Download.controller;

import com.Homis.ddeugae.common.util.BlobStorageManager;
import com.Homis.ddeugae.domain.Download.dto.FileDownloadReq;
import com.Homis.ddeugae.domain.Make.service.MakeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@RestController
@RequestMapping("/api/download")
@RequiredArgsConstructor
public class FileDownloadController {
    private final MakeService makeService;
    private final BlobStorageManager blobStorageManager;

    // [도안 제작] 이미지 파일 다운로드
    @PostMapping("/img")
    public ResponseEntity<StreamingResponseBody> downloadImgFile(
            HttpServletRequest request,
            @RequestBody FileDownloadReq downloadReq){
        Long userDataId = (Long) request.getAttribute("userDataId");
        Long madeDataId = downloadReq.getPostDataId();

        String blobUrl = makeService.getImgUrlMadePost(userDataId, madeDataId);

        String encodedFileName = URLEncoder.encode(downloadReq.getFileName(), StandardCharsets.UTF_8).replace("+", "%20");
        String downloadFileName = "Knit_Doa-" + encodedFileName + "-" +UUID.randomUUID() +".png";

        StreamingResponseBody responseBody = outputStream ->  {
            try (InputStream is = blobStorageManager.downloadBlobToStream(blobUrl)) {
                is.transferTo(outputStream);
            } catch (IOException ie) {
                throw ie; // custom하기 + log 남기기
            }
        };

        return ResponseEntity.ok()
                .header("Content-Disposition",
                        "attachment; filename*=UTF-8''" + downloadFileName)
                .contentType(MediaType.IMAGE_PNG)
                .body(responseBody);
    }
}