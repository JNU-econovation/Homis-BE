package com.Homis.ddeugae.domain.Make.dto;

public class MadeDto {
    private String madeName;
    private Integer size;
    private String madeImgUrl;

    public MadeDto(String name, Integer size, String imgUrl){
        if (name == null || name.isBlank() || size == null || imgUrl == null || imgUrl.isBlank()){
            throw new IllegalArgumentException("업로드(저장)할 도안 정보에 비어있는 값 발생");
        }
    }
}