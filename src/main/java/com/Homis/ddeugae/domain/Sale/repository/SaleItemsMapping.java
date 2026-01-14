package com.Homis.ddeugae.domain.Sale.repository;

import java.time.LocalDateTime;

public interface SaleItemsMapping {
    Long getSalePostId();
    String getSaleThumbnailImgUrl();
    String getSaleName();
    String getSalerNickname();
    Integer getSalePrice();
    LocalDateTime getCreatedAt();
}