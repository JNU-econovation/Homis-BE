package com.Homis.ddeugae.domain.Make.repository;

import java.time.LocalDateTime;

public interface MadePreviewMapping {
    long getMadeDataId();
    String getMadeName();
    String getMadeImgUrl();
    LocalDateTime getCreatedAt();
}