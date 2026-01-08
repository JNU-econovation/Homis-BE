package com.Homis.ddeugae.domain.Make.repository;

import com.Homis.ddeugae.domain.Make.entity.Made;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MadeRepository extends JpaRepository<Made, Long> {
    @Query(value = "SELECT made_data_id, made_name, made_img_url FROM made WHERE(maker_data_id=:user_data_id)", nativeQuery = true)
    List<MadePreviewMapping> findAllByMakerDataId(@Param("user_data_id") Long userDataID);

    @Query(value = "SELECT made_data_id, made_name, made_img_url, made_pdf_url, made_detail FROM made WHERE(made_data_id=:made_data_id)",
            nativeQuery = true)
    MadeDetailMapping findDetailByMadeDataId(@Param("made_data_id") Long madeDataId);
}