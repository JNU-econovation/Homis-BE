package com.Homis.ddeugae.domain.Purchase.repository;

public interface PurchasePreviewMapping {
    Long getPurchasedPostId();
    Long getSalePostId();
    String getSaleThumbnailImgUrl();
    String getSaleName();
    String getSalerNickname();
}