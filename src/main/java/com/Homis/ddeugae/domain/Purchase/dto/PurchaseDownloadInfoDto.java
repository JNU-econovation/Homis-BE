package com.Homis.ddeugae.domain.Purchase.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class PurchaseDownloadInfoDto {
    private String downloadFilename;
    private String downloadPdfUrl;
}
