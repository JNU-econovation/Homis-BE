package com.Homis.ddeugae.domain.Purchase.repository;

import com.Homis.ddeugae.domain.Purchase.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    boolean existsBySalePostIdAndPurchaserDataId(Long salePostId, Long purchaserDataId);
}
