package com.Homis.ddeugae.domain.Main.dto;

import com.Homis.ddeugae.common.enumType.MainPostType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MainPreviewTimeLineDto {
    private MainPostType type; // MADE(제작) 또는 PURCHASE(구매)
    private Long id;
    private String title;
    private String thumbnailUrl;
    private LocalDateTime createdAt;
}
