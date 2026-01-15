package com.Homis.ddeugae.domain.Sale.service;

import com.Homis.ddeugae.common.enumType.ErrorCode;
import com.Homis.ddeugae.common.exception.CustomException;
import com.Homis.ddeugae.common.util.BlobStorageManager;
import com.Homis.ddeugae.domain.Purchase.repository.PurchaseRepository;
import com.Homis.ddeugae.domain.Sale.dto.SaleAndUserInfoDto;
import com.Homis.ddeugae.domain.Sale.dto.SaleDetailResp;
import com.Homis.ddeugae.domain.Sale.dto.SaleUploadReq;
import com.Homis.ddeugae.domain.Sale.entity.Sale;
import com.Homis.ddeugae.domain.Sale.repository.SaleDetailMapping;
import com.Homis.ddeugae.domain.Sale.repository.SaleItemsMapping;
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
    final PurchaseRepository purchaseRepository;

    public void uploadSalePost(
            Long userDataId, MultipartFile thumbnail, List<MultipartFile> imgs, MultipartFile pdf, SaleUploadReq uploadReq){

        // 이미 interceptor에서 유뮤 확인 완
        final User userDoc = userRepository.findById(userDataId).get();

        String thumbnailUrl = blobStorageManager.uploadFileFromStream(thumbnail);
        String pdfUrl = blobStorageManager.uploadFileFromStream(pdf);

        Sale.SaleBuilder builder = Sale.builder()
                .saleName(uploadReq.getSaleName())
                .saleThumbnailImgUrl(thumbnailUrl)
                .salePdfUrl(pdfUrl)
                .saleScript(uploadReq.getSaleScript())
                .salePrice(uploadReq.getSalePrice())
                .saleType(uploadReq.getSaleType())
                .usedNeedle(uploadReq.getUsedNeedle())
                .saleSize(uploadReq.getSaleSize())
                .saleGauge(uploadReq.getSaleGauge())
                .yarnUsage(uploadReq.getYarnUsage())
                .purchasedCount(0).deleted(false)
                .salerNickname(userDoc.getUserNickname())
                .user(userDoc);

        // 여분 이미지는 없을 수 있음
        List<MultipartFile> validImgs = imgs == null ? List.of()
                : imgs.stream()
                .filter(file -> file != null && !file.isEmpty())
                .toList();

        if (!validImgs.isEmpty()) { // 빈 파일 객체가 왔을 경우도 거르기
            if (validImgs.size() > 3){ // 최대 3개
                throw new CustomException(ErrorCode.TOO_MANY_IMGS);
            }

            List<String> imgUrls = blobStorageManager.uploadFilesFromStream(imgs);
            builder.saleExtraImgUrls(imgUrls);
        }

        Sale salePost = builder.build();

        saleRepository.save(salePost);
    }

    public List<SaleItemsMapping> loadShoppingItems(){
        return saleRepository.findAllSaleItemsPreview();
    }

    public SaleDetailResp loadSaleDetail(Long userDataId, Long salePostId){
        Sale salePost = saleRepository.findById(salePostId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SALE));

        SaleDetailMapping detailData = saleRepository.findSaleDetail(salePostId);

        SaleAndUserInfoDto saleAndUserInfo = SaleAndUserInfoDto.builder()
                .uploader(userDataId.equals(salePost.getUserDataId()))
                .owner(purchaseRepository.existsBySalePostIdAndPurchaserDataId(salePostId, userDataId))
                .deleted(salePost.isDeleted())
                .build();

        return SaleDetailResp.builder()
                .saleAndUserInfo(saleAndUserInfo).salePostDetailData(detailData).build();
    }
}