package com.Homis.ddeugae.domain.Purchase.service;

import com.Homis.ddeugae.domain.Purchase.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
