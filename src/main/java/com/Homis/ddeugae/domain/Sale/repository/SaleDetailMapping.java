package com.Homis.ddeugae.domain.Sale.repository;

import java.util.List;

public interface SaleDetailMapping {
    Long getSalePostId();
    String getSaleName();
    String getSaleThumbnailImgUrl();
    List<String> getSaleExtraImgUrls();
    String getSaleScript();
    Integer getSalePrice();
    String getSaleType();
    String getSaleSize();
    String getSaleGauge();
    String getUsedNeedle();
    String getYarnUsage();
    String getSalerNickname();
}