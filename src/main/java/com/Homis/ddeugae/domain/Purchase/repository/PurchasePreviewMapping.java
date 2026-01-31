package com.Homis.ddeugae.domain.Purchase.repository;

import java.time.LocalDateTime;

public interface PurchasePreviewMapping {
    Long getPurchasedPostId();
    Long getSalePostId();
    String getSaleThumbnailImgUrl();
    String getSaleName();
    String getSalerNickname();
    LocalDateTime getPurchasedAt();
    boolean getSaleDeleted();
}