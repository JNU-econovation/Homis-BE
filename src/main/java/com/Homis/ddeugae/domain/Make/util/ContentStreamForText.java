package com.Homis.ddeugae.domain.Make.util;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;

import java.io.IOException;

public class ContentStreamForText {
    private final PDDocument doc;
    private PDPage currPage;
    private PDPageContentStream contentStream;

    private PDFont font;
    private float fontSize;
    private float leading;

    private float padding = 50f;
    private float startX = padding;
    private float startY = 842f - padding;
    private float usableWidth;

    private float currY;


    public ContentStreamForText(PDDocument doc, PDPage startPage, PDFont font) {
        this.doc = doc;
        this.currPage = startPage;
        this.font = font;

        this.fontSize = 15f;            // 폰트 크기 15px
        this.leading = 1.4f * fontSize; // 줄간격 140%
        this.usableWidth = PDRectangle.A4.getWidth() - 2 * padding;

        this.contentStream = setting(doc, currPage);

        this.currY = startY;
    }

    // 세팅
    private PDPageContentStream setting(PDDocument doc, PDPage page){
        try {
            contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true, true);

            contentStream.setFont(this.font, fontSize);
            contentStream.setNonStrokingColor(0, 0, 0); // 검정색

            contentStream.beginText();
            contentStream.newLineAtOffset(startX, startY);

            return contentStream;
        } catch (IOException ie) {
            throw new RuntimeException(ie);
        }
    }

    // 페이지 추가
    private void newPage(){
        try{
            contentStream.endText();
            contentStream.close();

            currPage = new PDPage(PDRectangle.A4);
            doc.addPage(currPage);

            contentStream = setting(doc, currPage);

            currY = startY;

        } catch (IOException ie){
            throw new RuntimeException(ie);
        }
    }

    // 한줄 출력
    private void writeLine(String text){
        try{
            // 사용 가능 높이 다 썼으면 새 페이지 생성!
            if (currY - leading < padding) { newPage(); }

            contentStream.showText(text);
            contentStream.newLineAtOffset(0, -leading);
            currY -= leading;
        } catch (IOException ie){
            throw new RuntimeException(ie);
        }
    }

    public void close(){
        try {
            contentStream.endText();
            contentStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
