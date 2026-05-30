package com.jamipariseva.repository;

import com.jamipariseva.entity.RevenueLocationEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RevenueLocationRepository extends JpaRepository<RevenueLocationEntity, Long> {

    @Query("""
            SELECT r.lgdDistCode AS code, r.distNameEng AS nameEng
            FROM RevenueLocationEntity r
            WHERE r.lgdDistCode IS NOT NULL
            ORDER BY r.distNameEng
            """)
    List<LocationProjection> getDistricts();

    @Query("""
            SELECT r.lgdSubdivCode AS code, r.subdivNameEng AS nameEng
            FROM RevenueLocationEntity r
            WHERE r.lgdDistCode = :distCode AND r.lgdSubdivCode IS NOT NULL
            ORDER BY r.subdivNameEng
            """)
    List<LocationProjection> getSubDivisions(@Param("distCode") String distCode);

    @Query("""
            SELECT r.lgdCircleCode AS code, r.rsnameEng AS nameEng
            FROM RevenueLocationEntity r
            WHERE r.lgdSubdivCode = :subdivCode AND r.lgdCircleCode IS NOT NULL
            ORDER BY r.rsnameEng
            """)
    List<LocationProjection> getCircles(@Param("subdivCode") String subdivCode);

    @Query("""
            SELECT r.lgdTehsilCode AS code, r.tnameEng AS nameEng
            FROM RevenueLocationEntity r
            WHERE r.lgdCircleCode = :circleCode AND r.lgdTehsilCode IS NOT NULL
            ORDER BY r.tnameEng
            """)
    List<LocationProjection> getTehsils(@Param("circleCode") String circleCode);

    @Query("""
            SELECT r.lgdVillageCode AS code, r.mounameEng AS nameEng
            FROM RevenueLocationEntity r
            WHERE r.lgdTehsilCode = :tehsilCode AND r.lgdVillageCode IS NOT NULL
            ORDER BY r.mounameEng
            """)
    List<LocationProjection> getVillages(@Param("tehsilCode") String tehsilCode);
}
