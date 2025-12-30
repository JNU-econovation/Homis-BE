package com.Homis.ddeugae.domain.Make.service;

import com.Homis.ddeugae.common.util.BlobStorageUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MadeDesignImageService {
    private final HtmlRenderService htmlRenderService;
    private final BlobStorageUploader blobUploader;
}