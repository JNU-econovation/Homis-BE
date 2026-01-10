package com.Homis.ddeugae.common.enumType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileType {
    IMG("png"),
    PDF("pdf");

    private final String extension;
}