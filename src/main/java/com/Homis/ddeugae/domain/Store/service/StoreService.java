package com.Homis.ddeugae.domain.Store.service;

import com.Homis.ddeugae.common.enumType.ErrorCode;
import com.Homis.ddeugae.common.exception.CustomException;
import com.Homis.ddeugae.common.util.BlobStorageManager;
import com.Homis.ddeugae.domain.Purchase.entity.Purchase;
import com.Homis.ddeugae.domain.Purchase.repository.PurchaseRepository;
import com.Homis.ddeugae.domain.Sale.entity.Sale;
import com.Homis.ddeugae.domain.Sale.repository.SaleRepository;
import com.Homis.ddeugae.domain.Store.dto.StoreLoadResp;
import com.Homis.ddeugae.domain.Store.dto.StoreMyPageResp;
import com.Homis.ddeugae.domain.User.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class StoreService {
    private final SaleRepository saleRepository;
    private final UserRepository userRepository;
    private final PurchaseRepository purchaseRepository;
    private final BlobStorageManager blobStorageManager;

    public StoreMyPageResp loadMyPagePreview(Long userDataId){
        return new StoreMyPageResp(
                saleRepository.findUserItemsById(userDataId),
                userRepository.getUserProfileById(userDataId));
    }

    public StoreLoadResp loadStorePreview(Long userDataId, String salerNickname){
        if(salerNickname.equals(userRepository.findById(userDataId).get().getUserNickname())){
            // 내 스토어임.
            return new StoreLoadResp(saleRepository.findUserItemsById(userDataId),
                    userRepository.getUserProfileById(userDataId), true);
        }

        if(userRepository.findByUserNickname(salerNickname).isEmpty()){
            throw new CustomException(ErrorCode.NOT_FOUND_SALER); // 존재하지 않는 사용자의 닉네임
        }

        return new StoreLoadResp(saleRepository.findUserItemsByNickname(salerNickname),
                userRepository.getUserProfileByNickname(salerNickname), false);
    }

    @Transactional
    public void deleteSalePost(Long userDataId, Long salePostId){
        Sale salePost = saleRepository.findById(salePostId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SALE)); // 존재 X

        if(!userDataId.equals(salePost.getUserDataId())){
            throw new CustomException(ErrorCode.NOT_OWNER); // 판매자가 아님
        }

        if(salePost.isDeleted()){
            throw new CustomException(ErrorCode.ALREADY_DELETED_SALE); // 이미 삭제된 게시글
        }

        // 구매 게시글에 삭제 표시
        purchaseRepository.markSaleDeleted(salePostId);

        if (salePost.getPurchasedCount() == 0){ // 어차피 구매자가 없다면 기록까지 삭제해도 됨
            saleRepository.delete(salePost); // 판매 기록 삭제

            if (salePost.getSalePdfUrl()==null || salePost.getSaleThumbnailImgUrl() == null){
                throw new CustomException(ErrorCode.WRONG_SALE_RECORD);
            }

            blobStorageManager.fileDelete(salePost.getSalePdfUrl()); // pdf 파일 삭제
            blobStorageManager.fileDelete(salePost.getSaleThumbnailImgUrl()); // 대표 이미지 파일 삭제

            // 나머지 이미지 파일들도 있다면 삭제
            if (salePost.getSaleExtraImgUrls() != null && !salePost.getSaleExtraImgUrls().isEmpty()){
                for (String imgUrls : salePost.getSaleExtraImgUrls()){
                    blobStorageManager.fileDelete(imgUrls);
                }
            }

        } else {
            salePost.setDeleted(true);
            saleRepository.save(salePost);
        }
    }
}
