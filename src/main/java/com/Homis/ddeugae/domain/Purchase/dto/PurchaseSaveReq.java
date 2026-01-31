package com.Homis.ddeugae.domain.Purchase.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class PurchaseSaveReq {
    @NotNull(message = "구매하려는 판매 도안 게시글 ID가 없습니다.")
    private Long salePostId;
}
