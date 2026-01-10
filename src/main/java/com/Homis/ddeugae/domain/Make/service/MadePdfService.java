package com.Homis.ddeugae.domain.Make.service;


import com.Homis.ddeugae.common.exception.CustomException;
import com.Homis.ddeugae.common.exception.ErrorCode;
import com.Homis.ddeugae.common.util.BlobStorageManager;
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
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MadePdfService {
    private final BlobStorageManager blobUploader;

    private void addMadeImgToPdf(PDDocument doc, PDPage page, String madeImgUrl){
        try{
            RestTemplate restTemplate = new RestTemplate();
            byte[] imageBytes = restTemplate.getForObject(madeImgUrl, byte[].class);

            PDImageXObject madeImg = PDImageXObject.createFromByteArray(doc, imageBytes, UUID.randomUUID().toString());
            PDPageContentStream contentStream = new PDPageContentStream(doc, page);

            float padding = 50f;
            float pageWidth = page.getMediaBox().getWidth();
            float pageHeight = page.getMediaBox().getHeight();
            float usableWidth = pageWidth - 2 * padding;
            float usableHeight = pageHeight - 2 * padding;

            float imgWidth = madeImg.getWidth();
            float imgHeight = madeImg.getHeight();

            // 이미지 잘리지 않게 scale 값 정하기
            float widthScale = usableWidth / imgWidth;
            float heightScale = usableHeight / imgHeight;
            float scale = Math.min(widthScale, heightScale); // 이미지 비율 유지용 scale값

            float drawWidth = imgWidth * scale;
            float drawHeight = imgHeight * scale;

            float x = padding + ((usableWidth - drawWidth)/2);
            float y = pageHeight - padding - drawHeight;

            contentStream.drawImage(madeImg, x, y, drawWidth, drawHeight);

            contentStream.close();

        } catch (IOException ie){
            throw new CustomException(ErrorCode.FAILED_ADD_IMG_TO_PDF, ie);
        }

    }

    private void addMadeDetailToPdf(PDDocument doc, PDPage page, String madeDetail){
        try {
            InputStream fontStream = getClass().getResourceAsStream("/fonts/Pretendard-Regular.ttf");
            PDFont font = PDType0Font.load(doc, fontStream);

            ContentStreamForText contentStreamForText = new ContentStreamForText(doc, page, font);

            contentStreamForText.writeText(madeDetail);

            contentStreamForText.close();

        } catch (IOException ie) {
            throw new CustomException(ErrorCode.FAILED_LOAD_FONT, ie);
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
            throw new CustomException(ErrorCode.FAILED_SAVE_PDF, ie);
        } catch (Exception e){
            throw new CustomException(ErrorCode.FAILED_LOAD_PDF, e);
        }
    }

    // 상세 설명 없을 때 사용할 메소드
    public String createAndStorePdfExcludeDetail(String madeImgUrl){
        PDDocument doc = new PDDocument();

        PDPage imgPage = new PDPage(PDRectangle.A4);
        doc.addPage(imgPage);
        addMadeImgToPdf(doc, imgPage, madeImgUrl);

        byte[] pdfBytes = loadPdfBytes(doc);

        return blobUploader.fileUpload(pdfBytes, "application/pdf", "pdf");
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
