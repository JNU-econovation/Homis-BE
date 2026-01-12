package com.Homis.ddeugae.domain.Sale.entity;

import com.Homis.ddeugae.domain.User.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_post_id")
    private Long salePostId;

    @Column(nullable = false)
    private String saleName;

    @Column(nullable = false)
    private String saleImgUrls;

    @Column(nullable = false)
    private String salePdfUrl;

    @Column(nullable = false, columnDefinition = "VARCHAR(1000)")
    private String saleScript;

    @Column(nullable = false)
    private Integer salePrice;

    @Column(nullable = false)
    private String saleType;

    @Column(nullable = false)
    private String saleSize;

    @Column(nullable = false)
    private String saleGauge;

    @Column(nullable = false)
    private String yarnUsage;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private Integer purchasedCount;

    @Column(nullable = false)
    private String salerNickname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "saler_data_id", referencedColumnName = "user_data_id", nullable = false)
    private User user;   // User.java의 userDataId와 FK

    // 조회용 필드 - 판매자 ID
    @Column(name = "saler_data_id", insertable = false, updatable = false, nullable = false)
    private Long userDataId;
}