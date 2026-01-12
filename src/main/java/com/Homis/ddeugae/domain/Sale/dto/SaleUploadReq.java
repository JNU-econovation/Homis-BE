package com.Homis.ddeugae.domain.Sale.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaleUploadReq {
    @NotBlank(message = "상품명을 지정해야 합니다.")
    private String saleName;
    @NotBlank(message = "상품 유형을 지정해야 합니다.")
    private String saleType;
    @NotBlank(message = "사용한 바늘을 지정해야 합니다.")
    private String usedNeedle;
    @NotBlank(message = "실측 사이즈를 입력해야 합니다.")
    private String saleSize;
    @NotBlank(message = "게이지를 지정해야 합니다.")
    private String saleGauge;
    @NotBlank(message = "실 소요량을 지정해야 합니다.")
    private String yarnUsage;
    @NotBlank(message = "")
    private String saleScript;
    private Integer salePrice;
}
