package com.Homis.ddeugae.domain.Make.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MadeFileDownloadInfoDto {
    private final String blobUrl;
    private final String extension;
}
