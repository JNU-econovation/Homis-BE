package com.Homis.ddeugae.domain.Purchase.repository;

import com.Homis.ddeugae.domain.Purchase.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    boolean existsBySalePostIdAndPurchaserDataId(Long salePostId, Long purchaserDataId);

    @Query(value = "SELECT purchased_post_id, sale_post_id, sale_thumbnail_img_url, sale_name, saler_nickname FROM purchase WHERE(purchaser_data_id=:purchaser_data_id)"
            , nativeQuery = true)
    List<PurchasePreviewMapping> findAllByPurchaserDataId(@Param("purchaser_data_id") Long purchaserDataId);
}