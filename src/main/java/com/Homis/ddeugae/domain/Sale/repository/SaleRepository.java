package com.Homis.ddeugae.domain.Sale.repository;

import com.Homis.ddeugae.domain.Sale.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query(value = "SELECT sale_post_id, sale_thumbnail_img_url, sale_name, saler_nickname, sale_price, created_at FROM sale WHERE(deleted=false)", nativeQuery = true)
    List<SaleItemsMapping> findAllSaleItemsPreview();
}