package com.Homis.ddeugae.domain.Sale.repository;

import java.time.LocalDateTime;

public interface SaleItemsMapping {
    Long getSalePostId();
    String getSaleThumbnailImgUrl();
    String getSaleName();
    String getSalerNickname();
    LocalDateTime getCreatedAt();
}