package com.Homis.ddeugae.domain.Make.service;


import com.Homis.ddeugae.common.util.BlobStorageUploader;
import com.Homis.ddeugae.domain.Make.util.ContentStreamForText;
import lombok.RequiredArgsConstructor;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
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

    private void addMadeDetailToPdf(PDDocument doc, PDPage page, String madeDetail){
        try {
            PDPageContentStream pageContentStream = new PDPageContentStream(doc, page,
                    PDPageContentStream.AppendMode.APPEND, true, true);

            PDFont font = PDType0Font.load(doc,
                    new File("C:\\Users\\user\\Homis-BE\\ddeugae\\src\\main\\resources\\fonts\\Pretendard-Medium.ttf"));

            ContentStreamForText contentStreamForText = new ContentStreamForText(pageContentStream, font);

            // TODO: PDF 파일에 madeDetail 내용 작성

            contentStreamForText.close();

        } catch (IOException ie) {
            throw new RuntimeException(ie);
        }
    }

    private byte[] loadPdfBytes(PDDocument doc){
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            doc.save(baos);
            doc.close();
            byte[] bytes = baos.toByteArray();

            return bytes;
        } catch (IOException ie) {
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
        addMadeDetailToPdf(doc, detailPage, madeDetail);

        byte[] pdfBytes = loadPdfBytes(doc);

        return blobUploader.fileUpload(pdfBytes, "application/pdf", "pdf");
    }
}
