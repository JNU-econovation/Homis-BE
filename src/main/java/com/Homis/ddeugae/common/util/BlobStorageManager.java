package com.Homis.ddeugae.common.util;

import com.Homis.ddeugae.common.exception.CustomException;
import com.Homis.ddeugae.common.exception.ErrorCode;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.azure.storage.blob.models.BlobStorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.UUID;

@Component
public class BlobStorageManager {
    private final BlobContainerClient containerClient; // homis-file 컨테이너와 연결될 클라이언트
    @Value("${spring.cloud.azure.storage.blob.prefix-url}")
    private String UrlPrefix;

    // spring cloud azure를 이용해 설정해둔 이름과 일치하는 컨테이너 클라이언트 가져와 설정
    public BlobStorageManager(BlobContainerClient blobServiceClient,
                              @Value("${spring.cloud.azure.storage.blob.container-name}")
                               String containerName){
        this.containerClient =
                blobServiceClient.getServiceClient().getBlobContainerClient(containerName);
    }
    
    // ---다운로드
    /**
     * Blob Storage에 있는 blobName의 blob(파일) 다운로드 스트림 반환
     *
     * @param blobUrl : 다운로드할 파일, blob의 URL
     * @return : Blob을 다운로드한 OutputStream 개체
     */
    public OutputStream downloadBlobToStream(String blobUrl){
        BlobClient blobClient = containerClient.getBlobClient(blobUrl.substring(UrlPrefix.length()));

        // 존재하는 blob이라면 outputstream 담아서 반환
    }

    // ---삭제
    /**
     * Blob Storage에 있는 blobName의 blob(파일) 삭제
     *
     * @param blobUrl : 삭제할 파일, blob의 URL
     */
    public void fileDelete(String blobUrl){
        try {
            BlobClient blobClient = containerClient.getBlobClient(blobUrl.substring(UrlPrefix.length()));
            boolean existence = blobClient.deleteIfExists();
            if (!existence){
                throw new CustomException(ErrorCode.BLOB_NOT_FOUND);
            }
        } catch (BlobStorageException be){
            throw new CustomException(ErrorCode.BLOB_FAILED_DELETE, be);
        } catch (Exception e){
            throw new CustomException(ErrorCode.UNKNOWN_FAILED_DELETE_FILE, e);
        }
    }
    
    // --- 업로드
    // TODO: InputStream으로 업로드하는 메소드 추가 [도안 판매 등록]

    /**
     * [도안 제작] 업로드할 파일, Content-Type, 확장자 받아 Blob Storage에 저장
     *
     * @param data : 이미지(byte array) 등의 파일 바이트
     * @param contentType : e.g. "image/png", "application/pdf"
     * @param extension : 확장자명 e.g. "png", "pdf"
     * @return : 업로드된 Blob URL -> DB에 저장될 예정
     */
    public String fileUpload(byte[] data, String contentType, String extension){
        try{
            String filename = "made-" + UUID.randomUUID() + "." + extension; // UUID 통해 저장될 파일명 생성 -> 충돌 방지

            BlobClient blobClient = containerClient.getBlobClient(filename); // 생성한 파일명으로 Blob 객체 가져옴

            // Blob에 데이터 업로드
            blobClient.upload(
                    new ByteArrayInputStream(data),
                    data.length,
                    true // 같은 파일명일 시 덮어쓰기 (잘 없긴 하겠지만...)
            );

            // 브라우저에서 파일 직접 열 때 올바르게 처리되도록 헤더 설정
            blobClient.setHttpHeaders(
                    new BlobHttpHeaders().setContentType(contentType)
            );

            return blobClient.getBlobUrl(); // 업로드된 Blob URL -> DB 저장, 이걸로 읽기 및 다운로드 가능!
        } catch (BlobStorageException be){
            throw new CustomException(ErrorCode.BLOB_FAILED_UPLOAD_FILE, be);
        } catch (Exception e){
            throw new CustomException(ErrorCode.UNKNOWN_FAILED_UPLOAD_FILE, e);
        }
    }
}
