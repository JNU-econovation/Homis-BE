package com.Homis.ddeugae.domain.Make.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MadeFileDownloadReq {
    @NotNull(message = "다운로드 받을 파일의 도안 게시글 ID가 없습니다.")
    private Long madeDataId;
    @NotBlank(message = "다운로드 받을 도안의 이름이 없습니다.")
    private String madeName;
}