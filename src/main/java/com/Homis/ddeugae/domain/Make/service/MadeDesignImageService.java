package com.Homis.ddeugae.domain.Make.service;

import com.Homis.ddeugae.common.exception.CustomException;
import com.Homis.ddeugae.common.exception.ErrorCode;
import com.Homis.ddeugae.common.util.BlobStorageUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Service
@RequiredArgsConstructor
public class MadeDesignImageService {
    private final WebSnapAPIService webSnapAPIService;
    private final BlobStorageUploader blobUploader;

    @Value("${websnap.auth.token}")
    private String websnapToken;

    /**
     * WebSnaAPI 결과인 이미지 url을 통해 파일 byte 가져오기 (blob storage 얿로드 목적)
     *
     * @param imageUrl : WebSnaAPI 결과인 이미지 url
     * @return : 이미지 파일 byte
     */
    private byte[] downloadImageBytes(String imageUrl) {
        try (InputStream in = new URL(imageUrl).openStream()) {
            return in.readAllBytes();
        } catch (IOException e) {
            throw new CustomException(ErrorCode.FAILED_DOWNLOAD_IMG);
        }
    }

    /**
     * 페이지 url -> 이미지 url -> 다운로드 -> blob storage 업로드 -> url (db 저장 예정)
     *
     * @param previewUrl : WebSnaAPI 요청에 담을 (렌더링 후 이미지 url 받을) preview 페이지 url
     * @return : blob storage에 업로드된 이미지 파일의 url
     */
    public String createAndStoreImage(String previewUrl) { // blob storage url 반환

        // WebSnapAPI로 URL -> image_url 반환
        String websnapImageUrl = webSnapAPIService.captureDesign(previewUrl, websnapToken);

        // 외부 image_url의 이미지 byte[] 다운로드
        byte[] imageBytes = downloadImageBytes(websnapImageUrl);

        // Azure Blob Storage에 이미지 업로드
        return blobUploader.fileUpload(imageBytes, "image/png", "png");
    }
}