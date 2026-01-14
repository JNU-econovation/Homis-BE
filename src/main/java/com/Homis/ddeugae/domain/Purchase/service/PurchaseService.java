package com.Homis.ddeugae.domain.Purchase.service;

import com.Homis.ddeugae.common.enumType.ErrorCode;
import com.Homis.ddeugae.common.exception.CustomException;
import com.Homis.ddeugae.domain.Make.entity.Made;
import com.Homis.ddeugae.domain.Purchase.entity.Purchase;
import com.Homis.ddeugae.domain.Purchase.repository.PurchasePreviewMapping;
import com.Homis.ddeugae.domain.Purchase.repository.PurchaseRepository;
import com.Homis.ddeugae.domain.Sale.entity.Sale;
import com.Homis.ddeugae.domain.Sale.repository.SaleRepository;
import com.Homis.ddeugae.domain.User.entity.User;
import com.Homis.ddeugae.domain.User.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final UserRepository userRepository;
    private final SaleRepository saleRepository;
    private final PurchaseRepository purchaseRepository;

    @Transactional
    public void purchaseSalePost(Long userDataId, Long salePostId){
        final User userDoc = userRepository.findById(userDataId).get(); // 이미 확인함 (interceptor에서)

        final Sale salePostDoc = saleRepository.findById(salePostId)
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

    public void deletePurchasePost(Long userDataId, Long purchasedPostId){
        Purchase purchase = checkExistenceAndOwner(userDataId, purchasedPostId);

        purchaseRepository.delete(purchase); // 삭제

        // 구매 횟수는 누적 횟수로 하기 위해 sale 레코드 내용을 수정하지 않음
    }
}
