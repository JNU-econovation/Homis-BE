package com.Homis.ddeugae.domain.Make.dto;

import lombok.Getter;

@Getter
public class MadeUploadResp {
    private final Long madeDataId;

    public MadeUploadResp(Long id){
        if(id== null){
            throw new IllegalArgumentException("도안 제작 업로드 request body data인 도안 제작 게시글 고유 ID가 null입니다.");
        }

        this.madeDataId = id;
    }
}
