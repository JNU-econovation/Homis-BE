package com.Homis.ddeugae.domain.Purchase.entity;

import com.Homis.ddeugae.domain.Sale.entity.Sale;
import com.Homis.ddeugae.domain.User.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_purchase_sale_user",
                        columnNames = {"sale_post_id", "purchaser_data_id"}
                )
        }
)
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchased_post_id")
    private Long purchasedPostId;

    @Column(nullable = false)
    private String saleName;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime purchasedAt;

    @Column(nullable = false)
    private String salerNickname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_post_id", referencedColumnName = "sale_post_id", nullable = false)
    private Sale salePost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchaser_data_id", referencedColumnName = "user_data_id", nullable = false)
    private User user;   // User.java의 userDataId와 FK

    //---조회용 필드
    // 구매 게시글 ID
    @Column(name = "sale_post_id", insertable = false, updatable = false, nullable = false)
    private Long salePostId;
    // 구매자 ID
    @Column(name = "purchaser_data_id", insertable = false, updatable = false, nullable = false)
    private Long purchaserDataId;
}