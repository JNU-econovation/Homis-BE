package com.Homis.ddeugae.domain.Sale.dto;

import com.Homis.ddeugae.domain.Sale.repository.SaleDetailMapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor @NoArgsConstructor
public class SaleDetailResp {
    private SaleDetailMapping salePostDetailData;
    private SaleAndUserInfoDto saleAndUserInfo;
}