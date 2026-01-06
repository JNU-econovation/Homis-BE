package com.Homis.ddeugae.domain.Make.util;


import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;

import java.io.IOException;

public class ContentStreamForText {
    private PDPageContentStream pageContentStream;
    private PDFont font;
    private float fontSize;
    private float margin;
    private float startY;
    private float pageWidth;
    private float usableWidth;
    private float leading;

    public ContentStreamForText(PDPageContentStream pageContentStream, PDFont font) {
        this.pageContentStream = pageContentStream;
        this.font = font;

        this.margin = 50;
        this.startY = 750;
        this.pageWidth = PDRectangle.A4.getWidth();
        this.usableWidth = pageWidth - 2 * margin;
        this.fontSize = 15f;            // 폰트 크기 15px
        this.leading = 1.4f * fontSize; // 줄간격 140%

        try {
            this.pageContentStream.setFont(this.font, this.fontSize);
            this.pageContentStream.setNonStrokingColor(0, 0, 0); // 검정색

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close(){
        try {
            this.pageContentStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
