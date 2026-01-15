package com.Homis.ddeugae.domain.Purchase.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDownloadReq {
    @NotNull(message = "구매한 게시글 ID가 없습니다.")
    private Long purchasedPostId;
}
