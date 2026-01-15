package com.Homis.ddeugae.domain.Sale.repository;

import com.Homis.ddeugae.domain.Sale.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query(value = "SELECT sale_post_id, sale_thumbnail_img_url, sale_name, saler_nickname, sale_price, created_at FROM sale WHERE(deleted=false)", nativeQuery = true)
    List<SaleItemsMapping> findAllSaleItemsPreview();

    @Query("""
SELECT s.salePostId AS salePostId,
       s.saleName AS saleName,
       s.saleThumbnailImgUrl AS saleThumbnailImgUrl,
       s.saleExtraImgUrls AS saleExtraImgUrls,
       s.saleScript AS saleScript,
       s.salePrice AS salePrice,
       s.saleType AS saleType,
       s.saleSize AS saleSize,
       s.saleGauge AS saleGauge,
       s.usedNeedle AS usedNeedle,
       s.yarnUsage AS yarnUsage,
       s.salerNickname AS salerNickname
FROM Sale s
WHERE s.salePostId = :sale_post_id
""")
    SaleDetailMapping findSaleDetail(@Param("sale_post_id") Long salePostId);

    @Query(value = """
            SELECT sale_post_id, sale_thumbnail_img_url, sale_name, saler_nickname, sale_price, created_at
            FROM sale WHERE(deleted=false AND (sale_name LIKE :txt OR saler_nickname LIKE :txt))
            """, nativeQuery = true)
    List<SaleItemsMapping> searchItemsByTxt(@Param("txt") String txt); // txt는 앞뒤로 %가 붙은 채 전달돼야 함

    @Query(value = """
            SELECT sale_post_id, sale_thumbnail_img_url, sale_name, saler_nickname, sale_price, created_at
            FROM sale WHERE(deleted=false AND saler_data_id=:user_data_id)
            """, nativeQuery = true)
    List<SaleItemsMapping> findUserItemsById(@Param("user_data_id") Long salerDataId);

    @Query(value = """
                SELECT sale_post_id, sale_thumbnail_img_url, sale_name, saler_nickname, sale_price, created_at
                FROM sale WHERE(deleted=false AND saler_nickname=:user_nickname)
                """, nativeQuery = true)
    List<SaleItemsMapping> findUserItemsByNickname(@Param("user_nickname") String salerNickname);
}