package com.Homis.ddeugae.domain.Sale.repository;

import com.Homis.ddeugae.domain.Sale.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}