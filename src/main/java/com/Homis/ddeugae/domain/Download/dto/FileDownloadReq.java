package com.Homis.ddeugae.domain.Download.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FileDownloadReq {
    @NotNull(message = "다운로드 받을 파일의 게시글 ID가 없습니다.")
    private Long postDataId;
    @NotBlank(message = "다운로드 받을 파일의 URL이 없습니다.")
    private String fileUrl;
}