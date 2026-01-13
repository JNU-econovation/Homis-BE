package com.Homis.ddeugae.domain.Sale.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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

    @NotBlank(message = "상품 설명을 작성해야 합니다.")
    private String saleScript;

    @NotNull(message = "상품 가격을 설정해야 합니다.")
    @Min(value = 0, message = "가격은 0원 이상이어야 합니다.")
    @Max(value = 0, message = "가격은 0원이어야 합니다.")
    private Integer salePrice;
}
