package com.Homis.ddeugae.domain.Purchase.service;

import com.Homis.ddeugae.common.enumType.ErrorCode;
import com.Homis.ddeugae.common.exception.CustomException;
import com.Homis.ddeugae.domain.Purchase.dto.PurchaseDownloadInfoDto;
import com.Homis.ddeugae.domain.Purchase.entity.Purchase;
import com.Homis.ddeugae.domain.Purchase.repository.PurchasePreviewMapping;
import com.Homis.ddeugae.domain.Purchase.repository.PurchaseRepository;
import com.Homis.ddeugae.domain.Sale.entity.Sale;
import com.Homis.ddeugae.domain.Sale.repository.SaleRepository;
import com.Homis.ddeugae.common.dto.DeletedFiles;
import com.Homis.ddeugae.domain.User.entity.User;
import com.Homis.ddeugae.domain.User.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final UserRepository userRepository;
    private final SaleRepository saleRepository;
    private final PurchaseRepository purchaseRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public void purchaseSalePost(Long userDataId, Long salePostId){
        final User userDoc = userRepository.findById(userDataId).get(); // 이미 확인함 (interceptor에서)

        Sale salePostDoc = saleRepository.findById(salePostId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SALE)); // 없는 등록 도안

        //---구매 불가능의 경우
        if (salePostDoc.getUserDataId().equals(userDataId)) { // 본인 등록 도안
            throw new CustomException(ErrorCode.OWN_SALE_POST);
        }
        if (salePostDoc.isDeleted()){ // 삭제된 도안
            throw new CustomException(ErrorCode.NOT_FOUND_SALE);
        }
        if (purchaseRepository.existsBySalePostIdAndPurchaserDataId(salePostId, userDataId)){
            throw new CustomException(ErrorCode.ALREADY_PURCHASE_POST);
        }

        Purchase purchase = Purchase.builder()
                .saleName(salePostDoc.getSaleName())
                .salerNickname(salePostDoc.getSalerNickname())
                .saleThumbnailImgUrl(salePostDoc.getSaleThumbnailImgUrl())
                .salePdfUrl(salePostDoc.getSalePdfUrl())
                .user(userDoc).salePost(salePostDoc)
                .build();

        purchaseRepository.save(purchase);

        // 구매된 횟수 올리기
        salePostDoc.setPurchasedCount(salePostDoc.getPurchasedCount()+1);
        saleRepository.save(salePostDoc);
    }

    public List<PurchasePreviewMapping> getPurchasedPreview(Long userDataId){
        return purchaseRepository.findAllByPurchaserDataId(userDataId);
    }

    private Purchase checkExistenceAndOwner(Long userDataId, Long purchasedPostId){
        Purchase purchasePost = purchaseRepository.findById(purchasedPostId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PURCHASED));

        // 도안 구매자 맞는지 확인
        if (!userDataId.equals(purchasePost.getPurchaserDataId())){
            throw new CustomException(ErrorCode.NOT_OWNER);
        }

        return purchasePost;
    }

    @Transactional
    public void deletePurchasePost(Long userDataId, Long purchasedPostId){
        Purchase purchase = checkExistenceAndOwner(userDataId, purchasedPostId);

        Sale salePostDoc = saleRepository.findById(purchase.getSalePostId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SALE)); // 없는 등록 도안

        purchaseRepository.delete(purchase); // 구매 삭제

        int purchased_cnt = salePostDoc.getPurchasedCount();

        if (purchased_cnt == 1 && salePostDoc.isDeleted()){
            // 방금 삭제한 사람이 마지막 소유자 & deleted == true -> blob 파일들 삭제, sale 레코드 삭제
            saleRepository.delete(salePostDoc); // 판매 기록 삭제

            applicationEventPublisher.publishEvent(DeletedFiles.fromSale(salePostDoc));
        } else {
            // 구매자 수 내리기
            salePostDoc.setPurchasedCount(purchased_cnt-1);
            saleRepository.save(salePostDoc);
        }
    }
    
    // 다운로드 정보 dto 생성
    public PurchaseDownloadInfoDto getDownloadInfo(Long userDataId, Long purchasedPostId){
        Purchase purchasePost = checkExistenceAndOwner(userDataId, purchasedPostId);
        
        String encodedFileName =
                URLEncoder.encode(purchasePost.getSaleName(), StandardCharsets.UTF_8).replace("+", "%20");
        
        String downloadFileName = "Knit_Doa-" + encodedFileName + "-" + UUID.randomUUID() + ".pdf";

        return new PurchaseDownloadInfoDto(downloadFileName, purchasePost.getSalePdfUrl());
    }
}