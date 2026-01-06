package com.Homis.ddeugae.domain.Make.service;


import com.Homis.ddeugae.common.util.BlobStorageUploader;
import lombok.RequiredArgsConstructor;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MadePdfService {
    private final BlobStorageUploader blobUploader;

    private void addMadeImgToPdf(PDDocument doc, PDPage page, String madeImgUrl){
        try{
            PDImageXObject madeImg = PDImageXObject.createFromFile(madeImgUrl, doc);
            PDPageContentStream contentStream = new PDPageContentStream(doc, page);
            contentStream.drawImage(madeImg, 50, 750, 595, 842); // TODO: width, height 조정 필요

            contentStream.close();

        } catch (IOException ie){
            throw new RuntimeException(ie);
        }

    }
    
    public String createAndStorePdf(String madeImgUrl, String madeDetail){
        PDDocument doc = new PDDocument();

        PDPage imgPage = new PDPage(PDRectangle.A4);
        doc.addPage(imgPage);
        addMadeImgToPdf(doc, imgPage, madeImgUrl);

        PDPage detailPage = new PDPage(PDRectangle.A4);
        doc.addPage(detailPage);
        // addMadeDetailToPdf(doc, detailPage, madeDetail);

        //byte[] pdfBytes = loadPdfBytes(doc);

        //return blobUploader.fileUpload(pdfBytes, "application/pdf", "pdf");
    }
}
