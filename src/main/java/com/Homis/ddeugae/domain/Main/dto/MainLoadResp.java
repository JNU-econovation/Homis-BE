package com.Homis.ddeugae.domain.Main.dto;

import com.Homis.ddeugae.common.enumType.MainPostType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class MainLoadResp {
    private MainPostType type; // MADE(제작) 또는 PURCHASE(구매)
    private Long id;
    private String title;
    private String thumbnailUrl;
    private LocalDateTime createdAt;
    private Long original_post_id;
    private String original_uploader_nickname;
    private boolean deleted;
}