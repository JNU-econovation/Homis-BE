package com.Homis.ddeugae.domain.Main.dto;

import com.Homis.ddeugae.domain.Make.repository.MadePreviewMapping;
import com.Homis.ddeugae.domain.Purchase.repository.PurchasePreviewMapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor @NoArgsConstructor
public class MainLoadResp {
    private List<MadePreviewMapping> madePreview;
    private List<PurchasePreviewMapping> purchasePreview;
}
