package com.Homis.ddeugae.domain.Main.service;

import com.Homis.ddeugae.common.enumType.MainPostType;
import com.Homis.ddeugae.domain.Main.dto.MainLoadResp;
import com.Homis.ddeugae.domain.Make.repository.MadePreviewMapping;
import com.Homis.ddeugae.domain.Make.service.MakeService;
import com.Homis.ddeugae.domain.Purchase.repository.PurchasePreviewMapping;
import com.Homis.ddeugae.domain.Purchase.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainService {
    private final MakeService makeService;
    private final PurchaseService purchaseService;

    public List<MainLoadResp> loadMainItems(Long userDataId){

        List<MadePreviewMapping> madePreviewData = makeService.getMadePreview(userDataId);
        List<PurchasePreviewMapping> purchasePreviewData = purchaseService.getPurchasedPreview(userDataId);

        List<MainLoadResp> items = new ArrayList<>();

        for(MadePreviewMapping made : madePreviewData){
            items.add(new MainLoadResp(
                    MainPostType.MADE,
                    made.getMadeDataId(), made.getMadeName(), made.getMadeImgUrl(), made.getCreatedAt(),
                    null, null
            ));
        }

        for(PurchasePreviewMapping purchase : purchasePreviewData){
            items.add(new MainLoadResp(
                    MainPostType.PURCHASE,
                    purchase.getPurchasedPostId(), purchase.getSaleName(), purchase.getSaleThumbnailImgUrl(), purchase.getPurchasedAt(),
                    purchase.getSalePostId(), purchase.getSalerNickname()
            ));
        }

        // 최신순 정렬
        items.sort(Comparator.comparing(MainLoadResp::getCreatedAt).reversed());

        return items;
    }
}
