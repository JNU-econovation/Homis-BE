package com.Homis.ddeugae.domain.Sale.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor @NoArgsConstructor
public class SaleAndUserInfoDto {
    private boolean uploader; // 등록자
    private boolean owner;    // 구매자
    private boolean deleted;  // 삭제 여부
}