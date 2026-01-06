package com.Homis.ddeugae.domain.Make.util;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            contentStream.setNonStrokingColor(new PDColor(new float[]{0, 0, 0}, PDDeviceRGB.INSTANCE)); // 검정색

            contentStream.beginText();
            contentStream.newLineAtOffset(startX, startY);

            return contentStream;
        } catch (IOException ie) {
            throw new RuntimeException(ie);
        }
    }

    public void writeText(String rawText){
        String[] lines = rawText.split("\\r?\\n"); // OS 상관 없이 엔터 기준 문장들 분리

        for (String line : lines){
            List<String> wrappedLines = wrapLine(line); // 폭 넘어가는 애들 안 넘어가게 처리

            for (String wrapped : wrappedLines) { // 처리된 애들로 한 줄씩 작성
                writeLine(wrapped);
            }

            nextLine(); // 사용자가 엔터친 곳 줄바꿈 +1
        }
    }

    // 페이지 추가
    private void newPage(){
        try{
            // 기존 페이지의 contentStream 마무리
            contentStream.endText();
            contentStream.close();

            // 새 페이지 생성 후 문서에 추가
            currPage = new PDPage(PDRectangle.A4);
            doc.addPage(currPage);

            // 새 페이지 기준 contentStream 재설정
            contentStream = setting(doc, currPage);

            currY = startY;

        } catch (IOException ie){
            throw new RuntimeException(ie);
        }
    }

    // 줄바꿈
    // 따로 뺄지 말지 고민했는데... writeText에서 반복되기도 하고 try catch 안 쓰려고 해당 메소드 작성함
    private void nextLine() {
        try {
            // 새로 쓸 내용의 위치를 줄 간격만큼 아래로 내리고 currY도 맞춰 조정
            contentStream.newLineAtOffset(0, -leading);
            currY -= leading;
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

            nextLine();
        } catch (IOException ie){
            throw new RuntimeException(ie);
        }
    }

    // 엔터되지 않은 한문장이 지정 폭을 넘으면 자동 줄바꿈이 되도록 처리
    private List<String> wrapLine(String line){
        List<String> res = new ArrayList<>();
        StringBuilder curr = new StringBuilder();

        // 문장을 한 글자씩 쪼개서 사용 가능 너비가 넘지 않도록 처리
        for (char c : line.toCharArray()) {
            try {
                float width = font.getStringWidth(curr +
                        String.valueOf(c)) / 1000 * fontSize;

                if (width > usableWidth) { // 너비 넘음 -> 처리 문장 리스트에 추가, curr 비우기
                    res.add(curr.toString());
                    curr.setLength(0);
                }

                curr.append(c); // 너비를 넘지 않았다면 curr에 c(글자) 추가

            } catch (IOException ie) {
                throw new RuntimeException(ie);
            }
        }

        // 마지막 남은 내용(줄)까지 추가
        if (!curr.isEmpty()) {
            res.add(curr.toString());
        }

        return res;
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
