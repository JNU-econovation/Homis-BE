package com.Homis.ddeugae.common.dto;

import com.Homis.ddeugae.common.util.BlobStorageManager;
import com.Homis.ddeugae.domain.Make.entity.Made;
import com.Homis.ddeugae.domain.Sale.entity.Sale;

import java.util.List;

public record DeletedFiles(
    String pdfUrl,
    String thumbnailUrl,
    List<String> extraImgUrls)
{
    public static DeletedFiles fromSale(Sale sale){ // 삭제된 sale 레코드의 파일들
        return new DeletedFiles(sale.getSalePdfUrl(), sale.getSaleThumbnailImgUrl(), sale.getSaleExtraImgUrls());
    }

    public static DeletedFiles fromMade(Made made){ // 삭제된 made 레코드의 파일들
        return new DeletedFiles(made.getMadePdfUrl(), made.getMadeImgUrl(), List.of());
    }

    public void deleteAllFiles(BlobStorageManager blobStorageManager){
        // 삭제된 거 있으면 파일 삭제
        if(pdfUrl != null){
            blobStorageManager.fileDelete(pdfUrl);
        }
        if (thumbnailUrl != null){
            blobStorageManager.fileDelete(thumbnailUrl);
        }
        extraImgUrls.forEach(blobStorageManager::fileDelete);
    }
}