package com.Homis.ddeugae.domain.Sale.service;

import com.Homis.ddeugae.common.util.BlobStorageManager;
import com.Homis.ddeugae.domain.Sale.dto.SaleUploadReq;
import com.Homis.ddeugae.domain.Sale.entity.Sale;
import com.Homis.ddeugae.domain.Sale.repository.SaleRepository;
import com.Homis.ddeugae.domain.User.entity.User;
import com.Homis.ddeugae.domain.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleService {
    final BlobStorageManager blobStorageManager;
    final UserRepository userRepository;
    final SaleRepository saleRepository;

    public void uploadSalePost(
            Long userDataId, MultipartFile[] imgs, MultipartFile pdf, SaleUploadReq uploadReq){

        // 이미 interceptor에서 유뮤 확인 완
        final User userDoc = userRepository.findById(userDataId).get();

        List<String> imgUrls = blobStorageManager.uploadFilesFromStream(imgs);
        String pdfUrl = blobStorageManager.uploadFileFromStream(pdf);

        Sale salePost = Sale.builder()
                .saleName(uploadReq.getSaleName())
                .saleImgUrls(imgUrls)
                .salePdfUrl(pdfUrl)
                .saleScript(uploadReq.getSaleScript())
                .salePrice(uploadReq.getSalePrice())
                .saleType(uploadReq.getSaleType())
                .usedNeedle(uploadReq.getUsedNeedle())
                .saleSize(uploadReq.getSaleSize())
                .saleGauge(uploadReq.getSaleGauge())
                .yarnUsage(uploadReq.getYarnUsage())
                .purchasedCount(0)
                .salerNickname(userDoc.getUserNickname())
                .user(userDoc)
                .build();

        saleRepository.save(salePost);
    }
}