package com.Homis.ddeugae.domain.Make.service;


import com.Homis.ddeugae.common.util.BlobStorageUploader;
import lombok.RequiredArgsConstructor;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MadePdfService {
    private final BlobStorageUploader blobUploader;

    public String createAndStorePdf(String madeImgUrl, String madeDetail){
        PDDocument doc = new PDDocument();

        PDPage imgPage = new PDPage(PDRectangle.A4);
        doc.addPage(imgPage);
        // addMadeImgToPdf(doc, imgPage, madeImgUrl);

        PDPage detailPage = new PDPage(PDRectangle.A4);
        doc.addPage(detailPage);
        // addMadeDetailToPdf(doc, detailPage, madeDetail);

        //byte[] pdfBytes = loadPdfBytes(doc);

        //return blobUploader.fileUpload(pdfBytes, "application/pdf", "pdf");
    }
}
