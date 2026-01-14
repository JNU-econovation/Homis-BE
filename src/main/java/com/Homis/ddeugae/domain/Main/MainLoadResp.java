package com.Homis.ddeugae.domain.Main;

import com.Homis.ddeugae.domain.Make.repository.MadePreviewMapping;
import com.Homis.ddeugae.domain.Purchase.repository.PurchasePreviewMapping;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Builder
@AllArgsConstructor
public class MainLoadResp {
    private final List<MadePreviewMapping> madePreview;
    private final List<PurchasePreviewMapping> purchasePreview;
}
